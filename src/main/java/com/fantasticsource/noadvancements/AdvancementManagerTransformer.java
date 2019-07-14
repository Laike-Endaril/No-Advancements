package com.fantasticsource.noadvancements;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

public class AdvancementManagerTransformer implements ClassFileTransformer
{
    private Class cls;

    AdvancementManagerTransformer(Class cls)
    {
        this.cls = cls;
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] bytes)
    {
        if (classBeingRedefined == cls)
        {
            System.out.println("Transforming " + className + " (" + bytes.length + " bytes)");
            try
            {
                return AdvancementManagerASM.advancementManagerEditBytes();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return bytes;
    }
}
