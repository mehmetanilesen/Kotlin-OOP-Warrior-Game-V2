package com.example.kotlinoopcharactersystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.logging.Level

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val w1 = Warrior()
        w1.nameC = "Battal Gazi"
        val w2 = Khajit()
        w2.nameC = "Kedi Kadın"
        w2.lvl = 2
        val w3 = Emperior()
        w3.nameC  = "Kara Sovalye"
        w3.lvl = 3
        val w4 = Archer()
        w4.nameC  = "Legolas"
        w4.lvl = 4

        val wlist = arrayListOf<Characters>(w1,w2,w3,w4)

        val MyHero = Hero()
        MyHero.nameC = "MAE"

        val fight = WarField(MyHero)
        fight.enemyList = wlist
        fight.StartinScreen()
        fight.fight()
    }
}


interface CharacterSpec {
    var nameC: String?
    var message: String?
    var lvl: Int?
    var maxhealth: Int
    var health: Int?
    var power: Int?
    var defence: Int?
    var speed: Int?
    var attacktype: String?
    var luck : Int?

    fun Alive(): Boolean
    fun SetupMessage (incomingmessage : String)
    fun getlastMessage () : String?
    fun Damage() :Int
    fun Attack (Enemy : Characters)
    fun Defence (hit : Int,nameE : String)
    fun levelUp()
    fun HealthBar(): String
}
abstract class Characters() : CharacterSpec{
    override var nameC: String? = null
    override var message: String? = null
    override var lvl: Int? = null
    override var maxhealth: Int = 0
    override var health: Int? = null
    override var power: Int? = null
    override var defence: Int? = null
    override var speed: Int? = null
    override var attacktype: String? = null
    override var luck : Int?= null

    fun LuckGenerator() {
        luck = (0..250).random()
    }
    override fun Alive(): Boolean { return health != 0}
    override fun SetupMessage(incomingmessage: String) { message =incomingmessage}
    override fun getlastMessage(): String? {return message}
    override fun Damage(): Int {
        LuckGenerator()
        return ((lvl!! * (power!! + speed!! * 2))* luck!!)/100}
    override fun Attack(Enemy: Characters) {
        var injury = Damage()
        SetupMessage("ATTACK: $nameC gave damage of $injury with $attacktype")
        Enemy.Defence(injury,nameC!!)

    }
    override fun Defence(hit: Int, nameE: String) {
        if (hit > health!!) {
            health = 0
            SetupMessage("DEFENCE :${nameC} took damage of $hit from $nameE and DIED!!!!")
        } else {
            health = health!! - hit
            SetupMessage("${nameC} took damage of $hit from $nameE")

        }
    }
    override fun levelUp() {lvl= lvl?.plus(1)}
    override fun HealthBar(): String {
        val total : Int = 20
        val piece : Int = (maxhealth!!/total)
        var bar : String = "["
        var border : Int = 0
        if(Alive())
        {
            if ((health!!/piece +1) < piece && (health!!/piece +1) != 0)
            {
                border = (health!!/piece +1) + 1
            }
            else
            {
                border = (health!!/piece +1)
            }
            bar = bar.padEnd(border,'█')
            bar = bar.padEnd(21,'░')
        }
        else {
            bar = bar.padEnd(22,'░')
        }
        return "$bar]"
    }

}
class Hero(): Characters() {
    override var nameC: String? = ""
    override var message: String? = ""
    override var lvl: Int? = 1
    override var maxhealth: Int = 100
    override var health: Int? = 100
    override var power: Int? = 15
    override var defence: Int? = 10
    override var speed: Int? = 5
    override var attacktype: String? = "SWORD"

    var Faith : Int? = null
    var SuperPower: Boolean? = false
}
class Warrior() :Characters() {
    override var nameC: String? = ""
    override var message: String? = ""
    override var lvl: Int? = 1
    override var maxhealth: Int = 100
    override var health: Int? = 100
    override var power: Int? = 10
    override var defence: Int? = 10
    override var speed: Int? = 5
    override var attacktype: String? = "SWORD"
}
class Khajit() :Characters() {
    override var nameC: String? = ""
    override var message: String? = ""
    override var lvl: Int? = 1
    override var maxhealth: Int = 70
    override var health: Int? = 70
    override var power: Int? = 10
    override var defence: Int? = 1
    override var speed: Int? = 8
    override var attacktype: String? = "PAW"}
class Emperior() :Characters() {
    override var nameC: String? = ""
    override var message: String? = ""
    override var lvl: Int? = 1
    override var maxhealth: Int = 110
    override var health: Int? = 110
    override var power: Int? = 15
    override var defence: Int? = 5
    override var speed: Int? = 5
    override var attacktype: String? = "SPEAR"
}
class Archer() :Characters() {
    override var nameC: String? = ""
    override var message: String? = ""
    override var lvl: Int? = 1
    override var maxhealth: Int = 150
    override var health: Int? = 150
    override var power: Int? = 5
    override var defence: Int? = 2
    override var speed: Int? = 2
    override var attacktype: String? = "ARROW"
}
class WarField(val hero : Hero){

    var enemyList : ArrayList<Characters> = arrayListOf()
    fun StartinScreen() {
        print("\n" +
                "                                                                                             \n" +
                "                                                                                             \n" +
                "  ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ \n" +
                " |______|______|______|______|______|______|______|______|______|______|______|______|______|\n" +
                "                                                                                             \n" +
                "                                                                                             \n" +
                "                                                                                             \n" +
                "                                                                                             \n")
        print("\n" +
                " __          __     _____  _____ ____  _____             _____  ______ _   _          \n" +
                " \\ \\        / /\\   |  __ \\|_   _/ __ \\|  __ \\      /\\   |  __ \\|  ____| \\ | |   /\\    \n" +
                "  \\ \\  /\\  / /  \\  | |__) | | || |  | | |__) |    /  \\  | |__) | |__  |  \\| |  /  \\   \n" +
                "   \\ \\/  \\/ / /\\ \\ |  _  /  | || |  | |  _  /    / /\\ \\ |  _  /|  __| | . ` | / /\\ \\  \n" +
                "    \\  /\\  / ____ \\| | \\ \\ _| || |__| | | \\ \\   / ____ \\| | \\ \\| |____| |\\  |/ ____ \\ \n" +
                "     \\/  \\/_/    \\_\\_|  \\_\\_____\\____/|_|  \\_\\ /_/    \\_\\_|  \\_\\______|_| \\_/_/    \\_\\\n" +
                "                                                                                      \n" +
                "                                                                                      \n")
        print("\n" +
                "                                                                                             \n" +
                "                                                                                             \n" +
                "  ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ \n" +
                " |______|______|______|______|______|______|______|______|______|______|______|______|______|\n" +
                "                                                                                             \n" +
                "                                                                                             \n" +
                "                                                                                             \n" +
                "                                                                                             \n")
    }
    fun heroDefeat() {
         print("\n" +
                    "  _____  ______ ______ ______       _______ \n" +
                    " |  __ \\|  ____|  ____|  ____|   /\\|__   __|\n" +
                    " | |  | | |__  | |__  | |__     /  \\  | |   \n" +
                    " | |  | |  __| |  __| |  __|   / /\\ \\ | |   \n" +
                    " | |__| | |____| |    | |____ / ____ \\| |   \n" +
                    " |_____/|______|_|    |______/_/    \\_\\_|   \n" +
                    "                                            \n" +
                    "                                            \n")
        }
    fun heroWin(){print("\n" +
            " .----------------.  .----------------.  .-----------------.\n" +
            "| .--------------. || .--------------. || .--------------. |\n" +
            "| | _____  _____ | || |     _____    | || | ____  _____  | |\n" +
            "| ||_   _||_   _|| || |    |_   _|   | || ||_   \\|_   _| | |\n" +
            "| |  | | /\\ | |  | || |      | |     | || |  |   \\ | |   | |\n" +
            "| |  | |/  \\| |  | || |      | |     | || |  | |\\ \\| |   | |\n" +
            "| |  |   /\\   |  | || |     _| |_    | || | _| |_\\   |_  | |\n" +
            "| |  |__/  \\__|  | || |    |_____|   | || ||_____|\\____| | |\n" +
            "| |              | || |              | || |              | |\n" +
            "| '--------------' || '--------------' || '--------------' |\n" +
            " '----------------'  '----------------'  '----------------' \n")}
    fun fight() {
        var i : Int = 0
        println("--- ROUND ${1} ---")
        while (hero.Alive())
        {
            if(!enemyList[i].Alive()) {
                hero.health = hero.maxhealth
                hero.levelUp()
                i++
                println("\n--- ROUND ${i+1} ---")
            }
            hero.Attack(enemyList[i])
            println("\n${hero.getlastMessage()}")
            println(enemyList[i].getlastMessage())
            println("HEALTH:${hero.HealthBar()}\t\t${hero.nameC}")
            println("HEALTH:${enemyList[i].HealthBar()}\t\t${enemyList[i].nameC}")

            if(enemyList[i].Alive()) {
                enemyList[i].Attack(hero)
                println("\n${enemyList[i].getlastMessage()}")
                println(hero.getlastMessage())
                println("HEALTH:${hero.HealthBar()}\t\t${hero.nameC}")
                println("HEALTH:${enemyList[i].HealthBar()}\t\t${enemyList[i].nameC}\n")
            }
            if (!enemyList[enemyList.size-1].Alive()) {
                heroWin()
                break
            }
        }
        if(!hero.Alive()) heroDefeat()

    }

}







