package com.fantasticsource.noadvancements;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class FakeAdvancementList extends AdvancementList
{
    private final HashMap<ResourceLocation, Advancement> advancements = new HashMap<>();
    private final LinkedHashSet<Advancement> roots = new LinkedHashSet<>();

    @SideOnly(Side.CLIENT)
    public void removeAll(Set<ResourceLocation> ids)
    {
    }

    public void loadAdvancements(Map<ResourceLocation, Advancement.Builder> advancementsIn)
    {
    }

    public void clear()
    {
    }

    public Iterable<Advancement> getRoots()
    {
        return this.roots;
    }

    public Iterable<Advancement> getAdvancements()
    {
        return this.advancements.values();
    }

    @Nullable
    public Advancement getAdvancement(ResourceLocation id)
    {
        return null;
    }

    @SideOnly(Side.CLIENT)
    public void setListener(@Nullable AdvancementList.Listener listenerIn)
    {
    }
}
