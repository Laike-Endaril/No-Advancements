package com.fantasticsource.noadvancements;

import net.minecraft.advancements.AdvancementManager;
import org.objectweb.asm.*;

import java.io.*;

public class AdvancementManagerASM
{
    //Thanks to https://www.javacodegeeks.com/2012/02/manipulating-java-class-files-with-asm.html

    private static byte[] advancementManagerEditBytes = null;

    public static void main(String[] args) throws IOException
    {
        //Write the output to a class file
        new DataOutputStream(new FileOutputStream(new File("AdvancementManager.class"))).write(advancementManagerEditBytes());
    }

    public static byte[] advancementManagerEditBytes() throws IOException
    {
        if (advancementManagerEditBytes != null) return advancementManagerEditBytes;

        InputStream in = AdvancementManager.class.getResourceAsStream("/net/minecraft/advancements/AdvancementManager.class");
        ClassReader classReader = new ClassReader(in);
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);

        //Wrap the ClassWriter with our custom ClassVisitor
        ModifierClassWriter mcw = new ModifierClassWriter(Opcodes.ASM5, cw);
        classReader.accept(mcw, 0);

        advancementManagerEditBytes = cw.toByteArray();
        return advancementManagerEditBytes;
    }

    //Our class modifier class visitor. It delegate all calls to the super class
    //Only makes sure that it returns our MethodVisitor for every method
    public static class ModifierClassWriter extends ClassVisitor
    {
        private int api;

        public ModifierClassWriter(int api, ClassWriter cv)
        {
            super(api, cv);
            this.api = api;
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions)
        {
            MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);

            System.out.println(name);

//            if (name.equals("init") && desc.equals("(Ljava/lang/ThreadGroup;Ljava/lang/Runnable;Ljava/lang/String;JLjava/security/AccessControlContext;Z)V"))
//            {
//                return new ThreadInitVisitor(api, mv);
//            }

            return mv;
        }
    }
}
