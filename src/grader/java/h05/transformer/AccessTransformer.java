package h05.transformer;

import org.objectweb.asm.*;
import org.sourcegrade.jagr.api.testing.ClassTransformer;

public class AccessTransformer implements ClassTransformer {
    @Override
    public String getName() {
        return "AccessTransformer";
    }

    @Override
    public void transform(ClassReader reader, ClassWriter writer) {
        if(reader.getClassName().equals("h05/math/Rational")){
        reader.accept(new CV(Opcodes.ASM9, writer), 0);
        } else {
            reader.accept(writer, 0);
        }
    }

    private static int makePublic(int access) {
        return access & ~Opcodes.ACC_PRIVATE & ~Opcodes.ACC_PROTECTED & ~Opcodes.ACC_FINAL | Opcodes.ACC_PUBLIC;
    }

    private static class CV extends ClassVisitor {
        public CV(int api, ClassVisitor classVisitor) {
            super(api, classVisitor);
        }

        @Override
        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            super.visit(version, makePublic(access), name, signature, superName, interfaces);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            return super.visitMethod(makePublic(access), name, descriptor, signature, exceptions);
        }

        @Override
        public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
            return super.visitField(makePublic(access), name, descriptor, signature, value);
        }
    }
}
