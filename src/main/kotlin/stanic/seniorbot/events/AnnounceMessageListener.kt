package stanic.seniorbot.events

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent
import stanic.seniorbot.Main
import stanic.seniorbot.utils.event.event
import stanic.seniorbot.utils.reply
import stanic.seniorbot.utils.toMessage

fun JDA.registerMessageReactionListener() = event<MessageReactionAddEvent> {
    if (Main.instance.announce.containsKey(member?.id) && Main.instance.announce[member?.id]?.messageID == messageId) {
        if (reactionEmote.name == "✅") {
            val announce = Main.instance.announce[member!!.id]!!

            if (announce.isSaying) {
                val embed = EmbedBuilder()
                    .setTitle(announce.title)
                    .setDescription(announce.description.toMessage())
                guild.getTextChannelById("710495685295997049")?.reply(embed.build())

                textChannel.deleteMessageById(announce.messageID).queue()
                Main.instance.announce.remove(member!!.id)
                return@event
            }

            val embed = EmbedBuilder()
                .setTitle("Announce")
                .setDescription("**Title:** ${announce.title} \n\nSay the description in the chat.")
            textChannel.editMessageById(announce.messageID, embed.build()).queue()
            reaction.removeReaction(member!!.user).queue()

            announce.isSaying = true
            announce.sayingChannel = textChannel.id
        }
    }
}

fun JDA.registerMessageColletorListener() = event<GuildMessageReceivedEvent> {
    if (Main.instance.announce.containsKey(member?.id) && this.channel.id == Main.instance.announce[member?.id]?.sayingChannel) {
        val announce = Main.instance.announce[member!!.id]!!
        if (announce.isSaying) {
            announce.description.add(message.contentRaw.replace("@nl", "\n"))
            val embed = EmbedBuilder()
                .setTitle("Announce")
                .setDescription("**Title:** ${announce.title} \n**Description:** ${announce.description.toMessage()} \n\nSay the description in the chat.\n**Click in ✅ to finish**")
            channel.editMessageById(announce.messageID, embed.build()).queue()
            message.delete().queue()
        }
    }
}