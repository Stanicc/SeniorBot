package stanic.seniorbot.model

class Announce(
    var messageID: String,
    var title: String,
    var description: ArrayList<String>
) {

    var isSaying = false
    var sayingChannel = ""

}