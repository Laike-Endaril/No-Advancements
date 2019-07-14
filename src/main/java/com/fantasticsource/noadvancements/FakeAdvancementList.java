package com.fantasticsource.noadvancements;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;

public class FakeAdvancementList extends AdvancementList
{
    private static final Logger LOGGER = LogManager.getLogger();
    private final Map<ResourceLocation, Advancement> advancements = Maps.<ResourceLocation, Advancement>newHashMap();
    /**
     * All advancements that do not have a parent.
     */
    private final Set<Advancement> roots = Sets.<Advancement>newLinkedHashSet();
    private final Set<Advancement> nonRoots = Sets.<Advancement>newLinkedHashSet();
    private AdvancementList.Listener listener;

    @SideOnly(Side.CLIENT)
    private void remove(Advancement advancementIn)
    {
        for (Advancement advancement : advancementIn.getChildren())
        {
            this.remove(advancement);
        }

        LOGGER.info("Forgot about advancement " + advancementIn.getId());
        this.advancements.remove(advancementIn.getId());

        if (advancementIn.getParent() == null)
        {
            this.roots.remove(advancementIn);

            if (this.listener != null)
            {
                this.listener.rootAdvancementRemoved(advancementIn);
            }
        }
        else
        {
            this.nonRoots.remove(advancementIn);

            if (this.listener != null)
            {
                this.listener.nonRootAdvancementRemoved(advancementIn);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void removeAll(Set<ResourceLocation> ids)
    {
        for (ResourceLocation resourcelocation : ids)
        {
            Advancement advancement = this.advancements.get(resourcelocation);

            if (advancement == null)
            {
                LOGGER.warn("Told to remove advancement " + resourcelocation + " but I don't know what that is");
            }
            else
            {
                this.remove(advancement);
            }
        }
    }

    public void loadAdvancements(Map<ResourceLocation, Advancement.Builder> advancementsIn)
    {
    }

    public void clear()
    {
        this.advancements.clear();
        this.roots.clear();
        this.nonRoots.clear();

        if (this.listener != null)
        {
            this.listener.advancementsCleared();
        }
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
        return this.advancements.get(id);
    }

    @SideOnly(Side.CLIENT)
    public void setListener(@Nullable AdvancementList.Listener listenerIn)
    {
        this.listener = listenerIn;

        if (listenerIn != null)
        {
            for (Advancement advancement : this.roots)
            {
                listenerIn.rootAdvancementAdded(advancement);
            }

            for (Advancement advancement1 : this.nonRoots)
            {
                listenerIn.nonRootAdvancementAdded(advancement1);
            }
        }
    }
}
