package com.fantasticsource.noadvancements;

import net.minecraft.advancements.AdvancementManager;
import sun.management.Agent;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class AdvancementAgent extends Agent
{
    //Thanks to...
    //https://stackoverflow.com/questions/11898566/tutorials-about-javaagents
    //https://bukkit.org/threads/tutorial-extreme-beyond-reflection-asm-replacing-loaded-classes.99376/
    //https://www.baeldung.com/java-instrumentation
    //https://stackoverflow.com/questions/19009583/difference-between-redefine-and-retransform-in-javaagent


    public static void premain(String agentArgs, Instrumentation instrumentation)
    {
        //Called when using -javaagent JVM argument, eg. java -javaagent <agent jarname> -jar <main jarname>
        //Executes before the main jar, thus the method name
        //Requires proper manifest entries (see build.gradle)

        System.out.println("premain ==============================================================================================================================");
        submain(agentArgs, instrumentation);
    }

    public static void agentmain(String agentArgs, Instrumentation instrumentation)
    {
        //Called at any time during runtime...I'll try this out later
        //TODO https://docs.oracle.com/javase/8/docs/api/java/lang/instrument/package-summary.html

        System.out.println("agentmain =============================================================================================================================");
        submain(agentArgs, instrumentation);
    }

    private static void submain(String agentArgs, Instrumentation instrumentation)
    {
        instrumentation.addTransformer(new AdvancementManagerTransformer(AdvancementManager.class), true);


        //Retransform already-loaded classes in memory
        try
        {
            instrumentation.retransformClasses(AdvancementManager.class);
        }
        catch (UnmodifiableClassException e)
        {
            e.printStackTrace();
        }
    }
}
