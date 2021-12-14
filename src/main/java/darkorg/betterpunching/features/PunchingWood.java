package darkorg.betterpunching.features;

import darkorg.betterpunching.effects.ModEffects;
import darkorg.betterpunching.effects.custom.ModEffectInstance;
import darkorg.betterpunching.setup.Config;
import darkorg.betterpunching.util.ToolCheck;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@SuppressWarnings("unused")
public class PunchingWood {

    @SubscribeEvent
    public void breakSpeed(PlayerEvent.BreakSpeed event) {

        BlockState state = event.getState();
        PlayerEntity player = event.getPlayer();
        ItemStack stack = player.getHeldItemMainhand();

        DamageSource invalidpunching = new DamageSource("invalidpunching");

        if (!Config.punchingWoodEnabled.get()) {return;}

        if (state.isIn(BlockTags.LOGS) || state.isIn(BlockTags.PLANKS) || state.isIn(BlockTags.WOODEN_SLABS) || state.isIn(BlockTags.WOODEN_STAIRS)) {
            if (ToolCheck.isInvalidTool(state, stack)) {
                if (stack.isEmpty()) {
                    player.attackEntityFrom(invalidpunching, Config.wrongToolDamage.get().floatValue());
                    player.addPotionEffect(new ModEffectInstance(ModEffects.SPLINTER.get(), 200, 0));
                    if (Config.weaknessDebuffEnabled.get()) {
                        player.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 200, 0));
                    }
                    if (Config.miningFatigueDebuffEnabled.get()) {
                        player.addPotionEffect(new EffectInstance(Effects.MINING_FATIGUE, 200, 0));
                    }
                }
                event.setNewSpeed(0.0F);
            }
        }
    }
}