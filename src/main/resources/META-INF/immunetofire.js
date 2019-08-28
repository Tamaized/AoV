function initializeCoreMod() {
    return {
        'immunetofire': {
            'target': {
                'type': 'METHOD',
                'class': 'net.minecraft.entity.Entity',
                'methodName': Java.type("net.minecraftforge.coremod.api.ASMAPI").mapMethod('func_70045_F'),
                'methodDesc': '()Z'
            },
            'transformer': function (methodNode) {
                if (methodNode instanceof org.objectweb.asm.tree.MethodNode) { // Stupid way to cast in JS
                    var ASM = Java.type('net.minecraftforge.coremod.api.ASMAPI');
                    var Opcodes = Java.type('org.objectweb.asm.Opcodes');
                    methodNode.instructions.insertBefore(
                        ASM.findFirstInstruction(methodNode, Opcodes.IRETURN),
                        ASM.listOf(
                            new org.objectweb.asm.tree.VarInsnNode(Opcodes.ALOAD, 0), // PUSH this TO THE TOP OF THE STACK
                            new org.objectweb.asm.tree.MethodInsnNode( // INVOKE tamaized.aov.asm.ASMHooks#isImmuneToFire(Entity, boolean)
                                Opcodes.INVOKESTATIC,
                                'tamaized/aov/asm/ASMHooks',
                                'isImmuneToFire',
                                '(ZLnet/minecraft/entity/Entity;)Z',
                                false
                                )
                            )
                        );
                }
                return methodNode;
            }
        }
    }
}
