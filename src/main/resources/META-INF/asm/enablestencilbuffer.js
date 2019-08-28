function initializeCoreMod() {
    return {
        'enablestencilbuffer': {
            'target': {
                'type': 'METHOD',
                'class': 'net.minecraft.client.shader.Framebuffer',
                'methodName': Java.type("net.minecraftforge.coremod.api.ASMAPI").mapMethod('func_216492_b'),
                'methodDesc': '(IIZ)V'
            },
            'transformer': function (methodNode) {
                if (methodNode instanceof org.objectweb.asm.tree.MethodNode) { // Stupid way to cast in JS to avoid warnings and fix autocomplete
                    var ASM = Java.type('net.minecraftforge.coremod.api.ASMAPI');
                    var Opcodes = Java.type('org.objectweb.asm.Opcodes');
                    methodNode.instructions.insertBefore(
                        ASM.findFirstMethodCall(
                            methodNode,
                            ASM.MethodType.VIRTUAL,
                            'net/minecraft/client/shader/Framebuffer',
                            ASM.mapMethod('func_147611_b'),
                            '()V'
                            ),
                        ASM.listOf(
                            new org.objectweb.asm.tree.VarInsnNode(Opcodes.ALOAD, 0), // PUSH this TO THE TOP OF THE STACK
                            new org.objectweb.asm.tree.MethodInsnNode( // INVOKE tamaized.aov.asm.ASMHooks#enableStencilBuffer(Framebuffer)
                                Opcodes.INVOKESTATIC,
                                'tamaized/aov/asm/ASMHooks',
                                'enableStencilBuffer',
                                '(Lnet/minecraft/client/shader/Framebuffer;)V',
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
