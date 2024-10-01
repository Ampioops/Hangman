//Спрашиваем пользователя о начале игры или выходе из игры
//Начиная игру берем слово из базы слов и строим виселицу для начала игры
//Когда игрок угадывает букву -> буквы открываются в интерфейсе
//Когда игрок ошибается -> счетчик ошибок увеличивается и рисунок виселицы обновляется
//У игрока есть запас на совершение ошибок(6)
//Когда достигнут лимит ошибок -> поражение и возвращаемся к начальному интерфейсу
//Когда игрок отгадал слово -> победа и возвращаемся к начальному интерфейсу


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static int ErrorsNow = 0;
    private final static int MAXERRORS = 6;
    private static boolean UserHere = true;
    private static Scanner scanner = new Scanner(System.in);
    private static StringBuilder ChangingWord;
    private static StringBuilder instantWord;
    private static StringBuilder RememberedDisplay = new StringBuilder();
    private static int lettersGuessed = 0;


    public static void main(String[] args) {
        askToStartGame();
    }

    public static void askToStartGame(){
        while (UserHere) {
            System.out.println("Желаете начать новую игру?(1 - da, 2 - exit)");
             if (Integer.parseInt(scanner.nextLine()) == 1){
                 System.out.println("Игра началась!");
                 startRound();
             }else {
                 UserHere = false;
                 System.out.println("Bye bye!");
                 break;
             }
        }
        // while (userHere)
        //  start new game?
        // yes -> startRound
        // no -> exit
    }

    public static void startRound(){
        getNewWord();
        System.out.println(ChangingWord);
        refreshDisplay();
        while (ErrorsNow < MAXERRORS) {
            drawGallows();
            System.out.println(RememberedDisplay.toString());
            System.out.println("Введите букву: ");
            char letter = scanner.nextLine().charAt(0);
            if(checkValidLetter(letter)){
                printResultOfTry(letter);
            }else{
                ErrorsNow++;
            }
            if(lettersGuessed == ChangingWord.length()){
                System.out.println(RememberedDisplay.toString());
                System.out.println("Вы победили!");
                break;
            }else if (ErrorsNow==MAXERRORS){
                drawGallows();
                System.out.println("ВЫ проигралИ)");
                break;
            }
        }
        //getNewWord
        //Рисуем виселицу и количество букв в слове drawGallows
        //while(gameNotOver)
        //  askForLetter
        //  checkValidLetter
        //  checkGameState
        //  drawGallows
    }

    public static void printResultOfTry(char letter){
        if (letter != ' ') {
            int i = 0;
            while (ChangingWord.indexOf(String.valueOf(letter), i) >= 0) {
                RememberedDisplay.setCharAt(ChangingWord.indexOf(String.valueOf(letter), i) + ChangingWord.indexOf(String.valueOf(letter), i) + 2, letter);
                ChangingWord.setCharAt(ChangingWord.indexOf(String.valueOf(letter), i), ' ');
                i = ChangingWord.indexOf(String.valueOf(letter), i) + 1;
                lettersGuessed++;
            }
        }
    }

    public static void refreshDisplay(){
        ErrorsNow = 0;
        UserHere = true;
        lettersGuessed = 0;
        RememberedDisplay.delete(0, RememberedDisplay.length());
        RememberedDisplay.append("| ");
        for(int j = 0; j < ChangingWord.length(); j++){
            RememberedDisplay.append("- ");
        }
        RememberedDisplay.append("|");
    }

    public static Boolean checkValidLetter(char letter){
        return instantWord.indexOf(String.valueOf(letter)) >= 0 && letter != ' ';
        //Получаем букву от пользователя
        //Проверяем наличие буквы в слове
        //Нет буквы -> добавить +1 к количеству ошибок
        //Есть буквы -> открыть их пользователю
    }

    public static void drawGallows(){
        //Рисуем
        switch (ErrorsNow){
            case 0:{
                System.out.println(" |------| ");
                System.out.println(" |      | ");
                System.out.println(" |        ");
                System.out.println(" |        ");
                System.out.println(" |        ");
                System.out.println("/-\\        ");
                break;
            }
            case 1:{
                System.out.println(" |------| ");
                System.out.println(" |      | ");
                System.out.println(" |      o ");
                System.out.println(" |        ");
                System.out.println(" |        ");
                System.out.println("/-\\        ");
                break;
            }
            case 2:{
                System.out.println(" |------| ");
                System.out.println(" |      | ");
                System.out.println(" |      o ");
                System.out.println(" |      x ");
                System.out.println(" |        ");
                System.out.println("/-\\        ");
                break;
            }
            case 3:{
                System.out.println(" |------| ");
                System.out.println(" |      | ");
                System.out.println(" |      o ");
                System.out.println(" |     /x ");
                System.out.println(" |        ");
                System.out.println("/-\\        ");
                break;
            }
            case 4:{
                System.out.println(" |------| ");
                System.out.println(" |      | ");
                System.out.println(" |      o ");
                System.out.println(" |     /x\\ ");
                System.out.println(" |        ");
                System.out.println("/-\\        ");
                break;
            }
            case 5:{
                System.out.println(" |------| ");
                System.out.println(" |      | ");
                System.out.println(" |      o ");
                System.out.println(" |     /x\\ ");
                System.out.println(" |     /  ");
                System.out.println("/-\\        ");
                break;
            }
            case 6:{
                System.out.println(" |------| ");
                System.out.println(" |      | ");
                System.out.println(" |      o ");
                System.out.println(" |     /x\\ ");
                System.out.println(" |     / \\ ");
                System.out.println("/-\\        ");
                break;
            }
        }
        // Отображение букв в виде |- - - - - - - |
    }

    public static StringBuilder getNewWord(){
        //Рандомно берем слово из словаря
        try {
            BufferedReader reader = new BufferedReader(new FileReader("/Users/eugene/IdeaProjects/Balda/src/russian-nouns.txt"));
            String word = reader.readLine();
            List<String> AllWords = new ArrayList<>();
            AllWords.add(word);
            while (word != null) {
                word = reader.readLine();
                AllWords.add(word);
            }
            reader.close();
            ChangingWord = new StringBuilder(AllWords.get((int) (Math.random() * AllWords.size())));
            instantWord = new StringBuilder(ChangingWord);
            return ChangingWord;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}