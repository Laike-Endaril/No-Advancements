package com.fantasticsource.noadvancements;

import com.fantasticsource.tools.ReflectionTool;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.util.EnumTypeAdapterFactory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.Field;

@Mod(modid = NoAdvancements.MODID, name = NoAdvancements.NAME, version = NoAdvancements.VERSION, dependencies = "required-after:fantasticlib@[1.12.2.021,)", acceptableRemoteVersions = "*")
public class NoAdvancements
{
    public static final String MODID = "noadvancements";
    public static final String NAME = "No Advancements";
    public static final String VERSION = "1.12.2.001";

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(NoAdvancements.class);

        try
        {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeHierarchyAdapter(Advancement.Builder.class, (JsonDeserializer<Advancement.Builder>) (p_deserialize_1_, p_deserialize_2_, p_deserialize_3_) -> null);
            builder.registerTypeAdapter(AdvancementRewards.class, new AdvancementRewards.Deserializer());
            builder.registerTypeHierarchyAdapter(ITextComponent.class, new ITextComponent.Serializer());
            builder.registerTypeHierarchyAdapter(Style.class, new Style.Serializer());
            builder.registerTypeAdapterFactory(new EnumTypeAdapterFactory());

            Field field = ReflectionTool.getField(AdvancementManager.class, "field_192783_b", "GSON");
            field.set(null, builder.create());


            Field field2 = ReflectionTool.getField(AdvancementManager.class, "field_192784_c", "ADVANCEMENT_LIST");
            field2.set(null, new FakeAdvancementList());
        }
        catch (NoSuchFieldException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public static void saveConfig(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equals(MODID)) ConfigManager.sync(MODID, Config.Type.INSTANCE);
    }
}
