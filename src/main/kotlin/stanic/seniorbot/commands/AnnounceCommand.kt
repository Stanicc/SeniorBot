package stanic.seniorbot.commands

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDA
import stanic.seniorbot.Main
import stanic.seniorbot.model.Announce
import stanic.seniorbot.utils.command.command
import stanic.seniorbot.utils.reply

fun JDA.registerAnnounceCommand() = command("announce") {
    if (Main.instance.announce.containsKey(author.id)) channel.reply(":x: | You already have an announce to make.")
    else {
        val args = message.contentRaw.split(" ")
        if (args.size == 1) {
            channel.reply(":x: | Use: !announce (Title)")
            return@command
        }

        var title = ""
        for (position in 1 until args.size) title = "$title${args[position]} "

        val embed = EmbedBuilder()
            .setTitle("Announce")
            .setDescription("\n**Title:** $title\n\nClick in ✅ to set the announce description")
        channel.reply(embed.build()) {
            addReaction("✅").queue()

            Main.instance.announce[this@command.author.id] = Announce(id, title, arrayListOf())
        }
    }
}