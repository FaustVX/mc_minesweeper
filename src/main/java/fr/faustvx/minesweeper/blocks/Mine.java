package fr.faustvx.minesweeper.blocks;

import fr.faustvx.minesweeper.Minesweeper;
import fr.faustvx.minesweeper.Utilities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class Mine extends Block
{
    private Mine()
    {
        super(Block.Properties.copy(Blocks.BEDROCK));
        super.registerDefaultState(defaultBlockState().setValue(REVEALED_PROPERTY, false).setValue(FLAGGED_PROPERTY, false));
        LOGGER.info("created block: {}", BLOCK.getId());
    }

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Minesweeper.MOD_ID);

    public static final RegistryObject<Block> BLOCK = BLOCKS.register("mine", Mine::new);

    public static final IntegerProperty MINES_PROPERTY = IntegerProperty.create("mines", 0, 8);
    public static final BooleanProperty REVEALED_PROPERTY = BooleanProperty.create("revealed");
    public static final BooleanProperty FLAGGED_PROPERTY = BooleanProperty.create("flagged");
    public static final BooleanProperty MINE_PROPERTY = BooleanProperty.create("mine");

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(MINES_PROPERTY, REVEALED_PROPERTY, FLAGGED_PROPERTY, MINE_PROPERTY);
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (!worldIn.isClientSide && !state.getValue(REVEALED_PROPERTY))
        {
            if (player.isCrouching())
            {
                Utilities.changeState(state, worldIn, pos, FLAGGED_PROPERTY, flag -> !flag);
            }
            else if (!state.getValue(FLAGGED_PROPERTY))
            {
                if (state.getValue(MINE_PROPERTY))
                {
                    ModifiableAttributeInstance maxHealth = player.getAttribute(Attributes.MAX_HEALTH);
                    if (maxHealth.getBaseValue() <= 2)
                    {
                        maxHealth.setBaseValue(20);
                        player.kill();
                    }
                    else
                    {
                        maxHealth.setBaseValue(maxHealth.getBaseValue() - 2);
                    }
                }
                Utilities.changeState(state, worldIn, pos, REVEALED_PROPERTY, r -> true);
            }
            return ActionResultType.CONSUME;
        }
        else
        {
            return ActionResultType.SUCCESS;
        }
    }
}
