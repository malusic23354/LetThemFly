package net.malusic.letthemfly

import com.mojang.brigadier.Command
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands

class CommandHandler(private val configuration: Configuration) {

    fun build() = Commands.literal("letthemfly")
        .requires { it.sender.hasPermission("letthemfly.reload") }
        .then(
            Commands.literal("reload").executes { ctx: com.mojang.brigadier.context.CommandContext<CommandSourceStack> ->
                configuration.reload()
                ctx.source.sender.sendRichMessage("<green>LetThemFly configuration reloaded.")
                Command.SINGLE_SUCCESS
            }
        )
        .build()
    
}