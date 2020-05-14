package stanic.seniorbot

import net.dv8tion.jda.api.JDABuilder
import stanic.seniorbot.commands.registerAnnounceCommand
import stanic.seniorbot.events.registerMessageColletorListener
import stanic.seniorbot.events.registerMessageReactionListener
import stanic.seniorbot.model.Announce

class Main {

    val announce = HashMap<String, Announce>()

    companion object {
        lateinit var instance: Main

        @JvmStatic
        fun main(args: Array<String>) {
            instance = Main()

            JDABuilder()
                .setToken("TOKEN HERE")
                .build().apply {
                    registerMessageColletorListener()
                    registerMessageReactionListener()
                    registerAnnounceCommand()
                }
        }
    }

}