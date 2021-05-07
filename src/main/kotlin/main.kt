import com.study.*

fun main(){
    println("==================== 코틀린 학습 프로그램 ==========================")
    println("원하는 날짜 입력시 해당 날짜 내용을 볼수 있음")
    println("1 -> Day01 , 2 -> Day02 숫자 입력시 해당 일자 학습내용을 불러옴")
    println("0 입력시 프로그램 종료")
    println("-100 입력시 학습 목록을 불러옴")
    println("=================================================================")
    while(true){
        print("학습 일자 입력 : ")
        var ck = readLine()!!.trim().toInt()
        if(ck == 0){ 
            println("시스템 종료")
            break
        }
        else if(ck==1) Day01()
        else if(ck==2) Day02()
        else if(ck==3) Day03()
        else if(ck==4) Day04()
        else if(ck==5) Day05()
        else if(ck==6) Day06()
        else if(ck==7) Day07()
        else if(ck==8) Day08()
        else if(ck==9) Day09()
        else if(ck==10) Day10()
        else if(ck==11) Day11()

        else if(ck==-100) StudyList()
        else if(ck==-1) GitTest()
        else if(ck==-2) GitTest_Teach()
    }
}