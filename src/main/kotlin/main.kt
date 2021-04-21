import com.study.*

fun main(){
    println("------ 코틀린 학습 프로그램 -------")
    println("원하는 날짜 입력시 해당 날짜 내용을 볼수 있음")
    println("ex) 1 입력시 Day01 학습내용을 불러옴")
    println("ex) 0 입력시 프로그램 종료")

    while(true){
        print("입력 : ")
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

    }
}