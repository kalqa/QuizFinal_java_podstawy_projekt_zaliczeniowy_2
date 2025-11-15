package pl.javaready.projects;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
package pl.javaready.projects;

import java.util.Scanner;

public class JavaBasicsQuiz {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Bank pytań
        String[][] questions = {
                {"Jaki jest domyślny typ dla liczb całkowitych w Javie?",
                        "a) byte", "b) short", "c) int", "d) long"},
                {"Który typ używamy do przechowywania pojedynczego znaku?",
                        "a) String", "b) char", "c) int", "d) byte"},
                {"Ile wartości może przyjąć typ boolean?",
                        "a) 2", "b) 4", "c) 8", "d) nieskończenie wiele"},
                {"Która konwencja nazewnictwa jest poprawna dla zmiennych w Javie?",
                        "a) snake_case", "b) camelCase", "c) PascalCase", "d) kebab-case"},
                {"Co wykona kod: int x = 5; x += 3;?",
                        "a) x = 5", "b) x = 3", "c) x = 8", "d) błąd kompilacji"},
                {"Która pętla wykona się przynajmniej raz, nawet jeśli warunek jest fałszywy?",
                        "a) for", "b) while", "c) do-while", "d) żadna"},
                {"Co robi słowo kluczowe 'break' w pętli?",
                        "a) Pomija bieżącą iterację", "b) Kończy całą pętlę", "c) Restartuje pętlę", "d) Nie robi nic"},
                {"Od którego indeksu zaczynają się tablice w Javie?",
                        "a) -1", "b) 0", "c) 1", "d) zależy od rozmiaru"},
                {"Jak utworzyć tablicę 5 liczb całkowitych?",
                        "a) int[] numbers = new int[5];", "b) int numbers[5];", "c) array<int> numbers = 5;", "d) int[] numbers = 5;"},
                {"Co oznacza słowo kluczowe 'void' przy funkcji?",
                        "a) Funkcja jest pusta", "b) Funkcja nic nie zwraca", "c) Funkcja jest prywatna", "d) Funkcja nie przyjmuje parametrów"},
                {"Który operator logiczny oznacza 'lub'?",
                        "a) &&", "b) ||", "c) !", "d) &"},
                {"Co zwróci wyrażenie: 10 / 3 w Javie (jeśli obie liczby to int)?",
                        "a) 3.33", "b) 3", "c) 4", "d) błąd"},
                {"Jaki typ danych służy do przechowywania tekstu?",
                        "a) char", "b) String", "c) text", "d) varchar"},
                {"Co to jest przeciążanie funkcji (overloading)?",
                        "a) Funkcja która zwraca za dużo danych", "b) Używanie tej samej nazwy funkcji z różnymi parametrami", "c) Funkcja która działa wolno", "d) Błąd kompilacji"},
                {"Która pętla jest najlepsza, gdy znamy z góry liczbę iteracji?",
                        "a) for", "b) while", "c) do-while", "d) wszystkie są równie dobre"}
        };

        String[] correctAnswers = {"c", "b", "a", "b", "c", "c", "b", "b", "a", "b", "b", "b", "b", "b", "a"};

        String[] explanations = {
                "W Javie domyślnym typem dla liczb całkowitych jest int.",
                "Typ char przechowuje pojedynczy znak w pojedynczym apostrofie 'A'.",
                "Boolean przyjmuje tylko dwie wartości: true lub false.",
                "W Javie zmienne piszemy camelCase: firstName, totalAmount.",
                "Operator += dodaje wartość do zmiennej. x += 3 to to samo co x = x + 3.",
                "Pętla do-while sprawdza warunek na końcu, więc wykona się minimum raz.",
                "Break natychmiast kończy wykonywanie pętli.",
                "Tablice w Javie są indeksowane od 0. Pierwszy element to array[0].",
                "Składnia tablicy: typ[] nazwa = new typ[rozmiar];",
                "Void oznacza, że funkcja nie zwraca żadnej wartości (tylko wykonuje akcje).",
                "Operator || (dwie pionowe kreski) oznacza 'lub' (OR).",
                "Dzielenie dwóch liczb int zwraca int (bez części dziesiętnej). 10/3 = 3.",
                "String (z dużej litery) służy do przechowywania ciągów znaków.",
                "Przeciążanie (overloading) to używanie tej samej nazwy funkcji z różnymi parametrami.",
                "Pętla for jest najlepsza, gdy znamy liczbę iteracji z góry."
        };

        boolean keepPlaying = true;

        while (keepPlaying) {
            displayWelcome();
            String userName = getUserName(scanner);
            displayRules();
            waitForEnter(scanner);

            int totalQuestions = questions.length;
            boolean[] wasCorrect = new boolean[totalQuestions];
            int correctCount = runQuiz(scanner, questions, correctAnswers, explanations, wasCorrect);

            displayResults(userName, correctCount, totalQuestions, wasCorrect, questions);

            keepPlaying = wantsToRetry(scanner);
        }

        displayGoodbye();
        scanner.close();
    }

    public static void displayWelcome() {
        System.out.println("");
        System.out.println("╔═══════════════════════════════════╗");
        System.out.println("║   QUIZ - JAVA BASICS (Część 1)    ║");
        System.out.println("╔═══════════════════════════════════╗");
        System.out.println("");
        System.out.println("Witaj w quizie sprawdzającym wiedzę z podstaw Javy!");
        System.out.println("Czeka na Ciebie 15 pytań z materiału Części 1 kursu.");
        System.out.println("");
    }

    public static String getUserName(Scanner scanner) {
        System.out.println("Jak masz na imię?");
        String name = scanner.nextLine().trim();

        while (isNameEmpty(name)) {
            System.out.println("Imię nie może być puste. Spróbuj ponownie:");
            name = scanner.nextLine().trim();
        }

        System.out.println("");
        System.out.println("Cześć " + name + "! Zaczynamy!");
        return name;
    }

    public static boolean isNameEmpty(String name) {
        return name.length() == 0;
    }

    public static void displayRules() {
        System.out.println("");
        System.out.println("=== ZASADY ===");
        System.out.println("• Na każde pytanie odpowiadasz literą: a, b, c lub d");
        System.out.println("• Po każdej odpowiedzi dowiesz się czy była poprawna");
        System.out.println("• Na końcu zobaczysz swój wynik i ocenę");
        System.out.println("");
        System.out.println("Naciśnij ENTER, gdy będziesz gotowy...");
    }

    public static void waitForEnter(Scanner scanner) {
        scanner.nextLine();
    }

    public static int runQuiz(Scanner scanner, String[][] questions, String[] correctAnswers,
                              String[] explanations, boolean[] wasCorrect) {
        int correctCount = 0;
        int totalQuestions = questions.length;

        for (int i = 0; i < totalQuestions; i++) {
            displayQuestionNumber(i + 1, totalQuestions);
            displayQuestion(questions[i]);

            String userAnswer = getValidAnswer(scanner);
            boolean isCorrect = checkAnswer(userAnswer, correctAnswers[i]);

            if (isCorrect) {
                correctCount++;
                wasCorrect[i] = true;
                displayCorrectFeedback();
            } else {
                wasCorrect[i] = false;
                displayIncorrectFeedback(correctAnswers[i]);
            }

            displayExplanation(explanations[i]);

            if (isNotLastQuestion(i, totalQuestions)) {
                waitForNextQuestion(scanner);
            }
        }

        return correctCount;
    }

    public static void displayQuestionNumber(int current, int total) {
        System.out.println("");
        System.out.println("═══════════════════════════════════");
        System.out.println("PYTANIE " + current + "/" + total);
        System.out.println("═══════════════════════════════════");
        System.out.println("");
    }

    public static void displayQuestion(String[] questionData) {
        System.out.println(questionData[0]);
        System.out.println("");

        for (int i = 1; i < questionData.length; i++) {
            System.out.println(questionData[i]);
        }

        System.out.println("");
    }

    public static String getValidAnswer(Scanner scanner) {
        System.out.println("Twoja odpowiedź (a/b/c/d):");
        String answer = scanner.nextLine().trim().toLowerCase();

        while (!isValidAnswer(answer)) {
            System.out.println("Nieprawidłowa odpowiedź. Wybierz a, b, c lub d:");
            answer = scanner.nextLine().trim().toLowerCase();
        }

        return answer;
    }

    public static boolean isValidAnswer(String answer) {
        return answer.equals("a") || answer.equals("b") ||
                answer.equals("c") || answer.equals("d");
    }

    public static boolean checkAnswer(String userAnswer, String correctAnswer) {
        return userAnswer.equals(correctAnswer);
    }

    public static void displayCorrectFeedback() {
        System.out.println("");
        System.out.println("✓ POPRAWNIE! Świetna robota!");
    }

    public static void displayIncorrectFeedback(String correctAnswer) {
        System.out.println("");
        System.out.println("✗ BŁĘDNA ODPOWIEDŹ! Poprawna odpowiedź to: " + correctAnswer);
    }

    public static void displayExplanation(String explanation) {
        System.out.println("");
        System.out.println("→ Wyjaśnienie: " + explanation);
    }

    public static boolean isNotLastQuestion(int currentIndex, int total) {
        return currentIndex < total - 1;
    }

    public static void waitForNextQuestion(Scanner scanner) {
        System.out.println("");
        System.out.println("Naciśnij ENTER, aby przejść do następnego pytania...");
        scanner.nextLine();
    }

    public static void displayResults(String userName, int correctCount, int totalQuestions,
                                      boolean[] wasCorrect, String[][] questions) {
        System.out.println("");
        System.out.println("═══════════════════════════════════");
        System.out.println("       TWOJE WYNIKI");
        System.out.println("═══════════════════════════════════");
        System.out.println("");

        displayScore(userName, correctCount, totalQuestions);
        displayPercentage(correctCount, totalQuestions);
        displayGrade(correctCount, totalQuestions);
        displayWrongAnswers(wasCorrect, questions);
    }

    public static void displayScore(String userName, int correct, int total) {
        System.out.println(userName + ", odpowiedziałeś poprawnie na " + correct + " z " + total + " pytań.");
    }

    public static void displayPercentage(int correct, int total) {
        double percentage = calculatePercentage(correct, total);
        System.out.println("Wynik: " + roundToTwoDecimals(percentage) + "%");
        System.out.println("");
    }

    public static double calculatePercentage(int correct, int total) {
        if (total == 0) {
            return 0.0;
        }
        return ((double) correct / total) * 100.0;
    }

    public static double roundToTwoDecimals(double value) {
        double decimalPlaces = 100.0;
        return Math.round(value * decimalPlaces) / decimalPlaces;
    }

    public static void displayGrade(int correct, int total) {
        double percentage = calculatePercentage(correct, total);
        String grade = getGradeDescription(percentage);

        System.out.println("OCENA: " + grade);
        System.out.println("");
    }

    public static String getGradeDescription(double percentage) {
        if (percentage >= 90) {
            return "Świetnie! Opanowałeś materiał!";
        } else if (percentage >= 70) {
            return "Dobrze! Jeszcze kilka tematów do powtórki.";
        } else if (percentage >= 50) {
            return "Nieźle, ale warto powtórzyć podstawy.";
        } else {
            return "Polecam wrócić do materiałów kursu.";
        }
    }

    public static void displayWrongAnswers(boolean[] wasCorrect, String[][] questions) {
        System.out.println("═══════════════════════════════════");
        System.out.println("  PYTANIA Z BŁĘDNYMI ODPOWIEDZIAMI");
        System.out.println("═══════════════════════════════════");
        System.out.println("");

        boolean hadWrongAnswers = false;

        for (int i = 0; i < wasCorrect.length; i++) {
            if (!wasCorrect[i]) {
                hadWrongAnswers = true;
                displaySingleWrongAnswer(i + 1, questions[i][0]);
            }
        }

        if (!hadWrongAnswers) {
            System.out.println("🎉 Brawo! Wszystkie odpowiedzi poprawne!");
        }

        System.out.println("");
    }

    public static void displaySingleWrongAnswer(int questionNumber, String questionText) {
        System.out.println("✗ Pytanie " + questionNumber + ": " + questionText);
    }

    public static boolean wantsToRetry(Scanner scanner) {
        System.out.println("═══════════════════════════════════");
        System.out.println("Czy chcesz spróbować ponownie? (tak/nie)");
        String answer = scanner.nextLine().trim().toLowerCase();
        return answer.equals("tak");
    }

    public static void displayGoodbye() {
        System.out.println("");
        System.out.println("═══════════════════════════════════");
        System.out.println("Dziękujemy za rozwiązanie quizu!");
        System.out.println("Pamiętaj: Praktyka czyni mistrza!");
        System.out.println("Powodzenia w nauce Javy! 🚀");
        System.out.println("═══════════════════════════════════");
        System.out.println("");
    }
}
