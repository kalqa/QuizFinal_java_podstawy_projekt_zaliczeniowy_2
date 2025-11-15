package pl.javaready.projects;

import java.util.Scanner;

public class JavaBasicsQuiz {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[][] questionOptions = {
                {"Jaki jest domyślny typ dla liczb całkowitych w Javie?",
                        "byte", "short", "int", "long"},
                {"Który typ używamy do przechowywania pojedynczego znaku?",
                        "String", "char", "int", "byte"},
                {"Ile wartości może przyjąć typ boolean?",
                        "2", "4", "8", "nieskończenie wiele"},
                {"Która konwencja nazewnictwa jest poprawna dla zmiennych w Javie?",
                        "snake_case", "camelCase", "PascalCase", "kebab-case"},
                {"Co wykona kod: int x = 5; x += 3;?",
                        "x = 5", "x = 3", "x = 8", "błąd kompilacji"},
                {"Która pętla wykona się przynajmniej raz, nawet jeśli warunek jest fałszywy?",
                        "for", "while", "do-while", "żadna"},
                {"Co robi słowo kluczowe 'break' w pętli?",
                        "Pomija bieżącą iterację", "Kończy całą pętlę", "Restartuje pętlę", "Nie robi nic"},
                {"Od którego indeksu zaczynają się tablice w Javie?",
                        "-1", "0", "1", "zależy od rozmiaru"},
                {"Jak utworzyć tablicę 5 liczb całkowitych?",
                        "int[] numbers = new int[5];", "int numbers[5];", "array<int> numbers = 5;", "int[] numbers = 5;"},
                {"Co oznacza słowo kluczowe 'void' przy funkcji?",
                        "Funkcja jest pusta", "Funkcja nic nie zwraca", "Funkcja jest prywatna", "Funkcja nie przyjmuje parametrów"},
                {"Który operator logiczny oznacza 'lub'?",
                        "&&", "||", "!", "&"},
                {"Co zwróci wyrażenie: 10 / 3 w Javie (jeśli obie liczby to int)?",
                        "3.33", "3", "4", "błąd"},
                {"Jaki typ danych służy do przechowywania tekstu?",
                        "char", "String", "text", "varchar"},
                {"Co to jest przeciążanie funkcji (overloading)?",
                        "Funkcja która zwraca za dużo danych", "Używanie tej samej nazwy funkcji z różnymi parametrami", "Funkcja która działa wolno", "Błąd kompilacji"},
                {"Która pętla jest najlepsza, gdy znamy z góry liczbę iteracji?",
                        "for", "while", "do-while", "wszystkie są równie dobre"}
        };
        int howManyOptionsToChoose = 4;
        int[] correctAnswerIndices = {2, 1, 0, 1, 2, 2, 1, 1, 0, 1, 1, 1, 1, 1, 0};
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
            int totalQuestions = getTotalQuestions(questionOptions);
            boolean[] correctAnswers = runQuiz(scanner, questionOptions, correctAnswerIndices, explanations, totalQuestions, howManyOptionsToChoose);
            int correctCount = countCorrectAnswers(correctAnswers);
            displayResults(userName, correctCount, totalQuestions, correctAnswers, questionOptions);
            keepPlaying = wantsToRetry(scanner);
        }
        displayGoodbye();
        scanner.close();
    }

    public static int getTotalQuestions(String[][] questionOptions) {
        return questionOptions.length;
    }

    public static boolean[] runQuiz(Scanner scanner, String[][] questionOptions, int[] correctAnswerIndices, String[] explanations, int totalQuestions, int howManyOptionsToChoose) {
        boolean[] wasCorrect = createWasCorrectArray(totalQuestions);
        for (int i = 0; i < totalQuestions; i++) {
            boolean isCorrect = proceedSingleQuestion(scanner, questionOptions, correctAnswerIndices, explanations, i, totalQuestions, howManyOptionsToChoose);
            wasCorrect[i] = isCorrect;
        }
        return wasCorrect;
    }

    public static boolean[] createWasCorrectArray(int totalQuestions) {
        return new boolean[totalQuestions];
    }

    public static boolean proceedSingleQuestion(Scanner scanner, String[][] questionOptions, int[] correctAnswerIndices, String[] explanations, int questionIndex, int totalQuestions, int howManyOptionsToChoose) {
        int questionNumber = calculateQuestionNumber(questionIndex);
        displayQuestionNumber(questionNumber, totalQuestions);
        int[] shuffledOrder = generateShuffledOrder(howManyOptionsToChoose);
        String[] shuffledQuestion = shuffleQuestionOptions(questionOptions[questionIndex], shuffledOrder);
        int newCorrectIndex = findNewCorrectIndex(correctAnswerIndices[questionIndex], shuffledOrder);
        displayQuestion(shuffledQuestion);
        String userAnswer = getValidAnswer(scanner);
        int userAnswerIndex = convertAnswerToIndex(userAnswer);
        boolean isCorrect = checkAnswerByIndex(userAnswerIndex, newCorrectIndex);
        displayFeedback(isCorrect, newCorrectIndex);
        displayExplanation(explanations[questionIndex]);
        if (isNotLastQuestion(questionIndex, totalQuestions)) {
            waitForNextQuestion(scanner);
        }
        return isCorrect;
    }

    public static int calculateQuestionNumber(int questionIndex) {
        return questionIndex + 1;
    }

    public static void displayFeedback(boolean isCorrect, int correctIndex) {
        if (isCorrect) {
            displayCorrectFeedback();
        } else {
            String correctLetter = convertIndexToAnswer(correctIndex);
            displayIncorrectFeedback(correctLetter);
        }
    }

    public static int[] generateShuffledOrder(int size) {
        int[] order = createOrderArray(size);
        int[] shuffled = shuffleOrderArray(order, size);
        return shuffled;
    }

    public static int[] createOrderArray(int size) {
        int[] order = new int[size];
        for (int i = 0; i < size; i++) {
            order[i] = i;
        }
        return order;
    }

    public static int[] shuffleOrderArray(int[] order, int size) {
        int[] result = copyArray(order);
        for (int i = size - 1; i > 0; i--) {
            int randomIndex = generateRandomIndex(i);
            result = swapAndReturn(result, i, randomIndex);
        }
        return result;
    }

    public static int[] copyArray(int[] original) {
        int[] copy = new int[original.length];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i];
        }
        return copy;
    }

    public static int generateRandomIndex(int maxIndex) {
        double random = Math.random() * (maxIndex + 1);
        int randomInt = (int) random;
        return randomInt;
    }

    public static int[] swapAndReturn(int[] array, int index1, int index2) {
        int[] result = copyArray(array);
        int temp = result[index1];
        result[index1] = result[index2];
        result[index2] = temp;
        return result;
    }

    public static String[] shuffleQuestionOptions(String[] originalQuestion, int[] order) {
        String[] result = createEmptyShuffledArray(originalQuestion);
        result = setQuestionText(result, originalQuestion);
        result = setShuffledAnswers(result, originalQuestion, order);
        return result;
    }

    public static String[] createEmptyShuffledArray(String[] originalQuestion) {
        return new String[originalQuestion.length];
    }

    public static String[] setQuestionText(String[] shuffled, String[] original) {
        String[] result = copyStringArray(shuffled);
        result[0] = original[0];
        return result;
    }

    public static String[] setShuffledAnswers(String[] shuffled, String[] original, int[] order) {
        String[] result = copyStringArray(shuffled);
        int numberOfAnswers = order.length;
        for (int i = 0; i < numberOfAnswers; i++) {
            result[i + 1] = original[order[i] + 1];
        }
        return result;
    }

    public static String[] copyStringArray(String[] original) {
        String[] copy = new String[original.length];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i];
        }
        return copy;
    }

    public static int findNewCorrectIndex(int originalCorrectIndex, int[] shuffledOrder) {
        for (int i = 0; i < shuffledOrder.length; i++) {
            if (isCorrectIndexAtPosition(shuffledOrder[i], originalCorrectIndex)) {
                return i;
            }
        }
        return 0;
    }

    public static boolean isCorrectIndexAtPosition(int shuffledValue, int originalCorrectIndex) {
        return shuffledValue == originalCorrectIndex;
    }

    public static int convertAnswerToIndex(String answer) {
        if (isAnswerA(answer)) {
            return 0;
        } else if (isAnswerB(answer)) {
            return 1;
        } else if (isAnswerC(answer)) {
            return 2;
        } else {
            return 3;
        }
    }

    public static boolean isAnswerA(String answer) {
        return answer.equals("a");
    }

    public static boolean isAnswerB(String answer) {
        return answer.equals("b");
    }

    public static boolean isAnswerC(String answer) {
        return answer.equals("c");
    }

    public static boolean isAnswerD(String answer) {
        return answer.equals("d");
    }

    public static String convertIndexToAnswer(int index) {
        if (isIndexZero(index)) {
            return "a";
        } else if (isIndexOne(index)) {
            return "b";
        } else if (isIndexTwo(index)) {
            return "c";
        } else {
            return "d";
        }
    }

    public static boolean isIndexZero(int index) {
        return index == 0;
    }

    public static boolean isIndexOne(int index) {
        return index == 1;
    }

    public static boolean isIndexTwo(int index) {
        return index == 2;
    }

    public static boolean checkAnswerByIndex(int userIndex, int correctIndex) {
        return userIndex == correctIndex;
    }

    public static int countCorrectAnswers(boolean[] wasCorrect) {
        int count = 0;
        for (int i = 0; i < wasCorrect.length; i++) {
            if (wasCorrect[i]) {
                count = incrementCount(count);
            }
        }
        return count;
    }

    public static int incrementCount(int count) {
        return count + 1;
    }

    public static void displayWelcome() {
        displayEmptyLine();
        displayWelcomeHeader();
        displayEmptyLine();
        displayWelcomeMessage();
        displayQuizDescription();
        displayEmptyLine();
    }

    public static void displayEmptyLine() {
        System.out.println("");
    }

    public static void displayWelcomeHeader() {
        System.out.println("╔═══════════════════════════════════╗");
        System.out.println("║   QUIZ - JAVA BASICS (Część 1)    ║");
        System.out.println("╔═══════════════════════════════════╗");
    }

    public static void displayWelcomeMessage() {
        System.out.println("Witaj w quizie sprawdzającym wiedzę z podstaw Javy!");
    }

    public static void displayQuizDescription() {
        System.out.println("Czeka na Ciebie 15 pytań z materiału Części 1 kursu.");
    }

    public static String getUserName(Scanner scanner) {
        displayNamePrompt();
        String name = readName(scanner);
        name = validateName(scanner, name);
        displayGreeting(name);
        return name;
    }

    public static void displayNamePrompt() {
        System.out.println("Jak masz na imię?");
    }

    public static String readName(Scanner scanner) {
        return scanner.nextLine();
    }

    public static String validateName(Scanner scanner, String name) {
        while (isNameEmpty(name)) {
            displayNameEmptyError();
            name = readName(scanner);
        }
        return name;
    }

    public static void displayNameEmptyError() {
        System.out.println("Imię nie może być puste. Spróbuj ponownie:");
    }

    public static void displayGreeting(String name) {
        displayEmptyLine();
        System.out.println("Cześć " + name + "! Zaczynamy!");
    }

    public static boolean isNameEmpty(String name) {
        return name.length() == 0;
    }

    public static void displayRules() {
        displayEmptyLine();
        displayRulesHeader();
        displayRuleAnswer();
        displayRuleRandomOrder();
        displayRuleFeedback();
        displayRuleResults();
        displayEmptyLine();
        displayReadyPrompt();
    }

    public static void displayRulesHeader() {
        System.out.println("=== ZASADY ===");
    }

    public static void displayRuleAnswer() {
        System.out.println("• Na każde pytanie odpowiadasz literą: a, b, c lub d");
    }

    public static void displayRuleRandomOrder() {
        System.out.println("• Kolejność odpowiedzi jest losowa dla każdego pytania!");
    }

    public static void displayRuleFeedback() {
        System.out.println("• Po każdej odpowiedzi dowiesz się czy była poprawna");
    }

    public static void displayRuleResults() {
        System.out.println("• Na końcu zobaczysz swój wynik i ocenę");
    }

    public static void displayReadyPrompt() {
        System.out.println("Naciśnij ENTER, gdy będziesz gotowy...");
    }

    public static void waitForEnter(Scanner scanner) {
        scanner.nextLine();
    }

    public static void displayQuestionNumber(int current, int total) {
        displayEmptyLine();
        displayQuestionSeparator();
        displayQuestionCounter(current, total);
        displayQuestionSeparator();
        displayEmptyLine();
    }

    public static void displayQuestionSeparator() {
        System.out.println("═══════════════════════════════════");
    }

    public static void displayQuestionCounter(int current, int total) {
        System.out.println("PYTANIE " + current + "/" + total);
    }

    public static void displayQuestion(String[] questionData) {
        displayQuestionText(questionData);
        displayEmptyLine();
        displayAnswerOptions(questionData);
        displayEmptyLine();
    }

    public static void displayQuestionText(String[] questionData) {
        System.out.println(questionData[0]);
    }

    public static void displayAnswerOptions(String[] questionData) {
        displayAnswerA(questionData[1]);
        displayAnswerB(questionData[2]);
        displayAnswerC(questionData[3]);
        displayAnswerD(questionData[4]);
    }

    public static void displayAnswerA(String text) {
        System.out.println("a) " + text);
    }

    public static void displayAnswerB(String text) {
        System.out.println("b) " + text);
    }

    public static void displayAnswerC(String text) {
        System.out.println("c) " + text);
    }

    public static void displayAnswerD(String text) {
        System.out.println("d) " + text);
    }

    public static String getValidAnswer(Scanner scanner) {
        displayAnswerPrompt();
        String answer = readAnswer(scanner);
        answer = validateAnswer(scanner, answer);
        return answer;
    }

    public static void displayAnswerPrompt() {
        System.out.println("Twoja odpowiedź (a/b/c/d):");
    }

    public static String readAnswer(Scanner scanner) {
        return scanner.nextLine();
    }

    public static String validateAnswer(Scanner scanner, String answer) {
        while (!isValidAnswer(answer)) {
            displayInvalidAnswerError();
            answer = readAnswer(scanner);
        }
        return answer;
    }

    public static void displayInvalidAnswerError() {
        System.out.println("Nieprawidłowa odpowiedź. Wybierz a, b, c lub d:");
    }

    public static boolean isValidAnswer(String answer) {
        return isAnswerA(answer) || isAnswerB(answer) || isAnswerC(answer) || isAnswerD(answer);
    }

    public static void displayCorrectFeedback() {
        displayEmptyLine();
        displayCorrectMessage();
    }

    public static void displayCorrectMessage() {
        System.out.println("✓ POPRAWNIE! Świetna robota!");
    }

    public static void displayIncorrectFeedback(String correctAnswer) {
        displayEmptyLine();
        displayIncorrectMessage(correctAnswer);
    }

    public static void displayIncorrectMessage(String correctAnswer) {
        System.out.println("✗ BŁĘDNA ODPOWIEDŹ! Poprawna odpowiedź to: " + correctAnswer);
    }

    public static void displayExplanation(String explanation) {
        displayEmptyLine();
        displayExplanationText(explanation);
    }

    public static void displayExplanationText(String explanation) {
        System.out.println("→ Wyjaśnienie: " + explanation);
    }

    public static boolean isNotLastQuestion(int currentIndex, int total) {
        return currentIndex < total - 1;
    }

    public static void waitForNextQuestion(Scanner scanner) {
        displayEmptyLine();
        displayNextQuestionPrompt();
        scanner.nextLine();
    }

    public static void displayNextQuestionPrompt() {
        System.out.println("Naciśnij ENTER, aby przejść do następnego pytania...");
    }

    public static void displayResults(String userName, int correctCount, int totalQuestions, boolean[] wasCorrect, String[][] questions) {
        displayResultsHeader();
        displayScore(userName, correctCount, totalQuestions);
        displayPercentage(correctCount, totalQuestions);
        displayGrade(correctCount, totalQuestions);
        displayWrongAnswers(wasCorrect, questions);
    }

    public static void displayResultsHeader() {
        displayEmptyLine();
        displayResultsSeparator();
        displayResultsTitle();
        displayResultsSeparator();
        displayEmptyLine();
    }

    public static void displayResultsSeparator() {
        System.out.println("═══════════════════════════════════");
    }

    public static void displayResultsTitle() {
        System.out.println("       TWOJE WYNIKI");
    }

    public static void displayScore(String userName, int correct, int total) {
        System.out.println(userName + ", odpowiedziałeś poprawnie na " + correct + " z " + total + " pytań.");
    }

    public static void displayPercentage(int correct, int total) {
        double percentage = calculatePercentage(correct, total);
        double rounded = roundToTwoDecimals(percentage);
        displayPercentageText(rounded);
        displayEmptyLine();
    }

    public static void displayPercentageText(double percentage) {
        System.out.println("Wynik: " + percentage + "%");
    }

    public static double calculatePercentage(int correct, int total) {
        if (isTotalZero(total)) {
            return 0.0;
        }
        return calculatePercentageValue(correct, total);
    }

    public static boolean isTotalZero(int total) {
        return total == 0;
    }

    public static double calculatePercentageValue(int correct, int total) {
        double percentageMultiplier = 100.0;
        return ((double) correct / total) * percentageMultiplier;
    }

    public static double roundToTwoDecimals(double value) {
        double decimalPlaces = 100.0;
        return Math.round(value * decimalPlaces) / decimalPlaces;
    }

    public static void displayGrade(int correct, int total) {
        double percentage = calculatePercentage(correct, total);
        String grade = getGradeDescription(percentage);
        displayGradeText(grade);
        displayEmptyLine();
    }

    public static void displayGradeText(String grade) {
        System.out.println("OCENA: " + grade);
    }

    public static String getGradeDescription(double percentage) {
        if (isExcellentGrade(percentage)) {
            return "Świetnie! Opanowałeś materiał!";
        } else if (isGoodGrade(percentage)) {
            return "Dobrze! Jeszcze kilka tematów do powtórki.";
        } else if (isFairGrade(percentage)) {
            return "Nieźle, ale warto powtórzyć podstawy.";
        } else {
            return "Polecam wrócić do materiałów kursu.";
        }
    }

    public static boolean isExcellentGrade(double percentage) {
        double excellentThreshold = 90;
        return percentage >= excellentThreshold;
    }

    public static boolean isGoodGrade(double percentage) {
        double goodThreshold = 70;
        return percentage >= goodThreshold;
    }

    public static boolean isFairGrade(double percentage) {
        double fairThreshold = 50;
        return percentage >= fairThreshold;
    }

    public static void displayWrongAnswers(boolean[] wasCorrect, String[][] questions) {
        displayWrongAnswersHeader();
        boolean hadWrongAnswers = displayWrongAnswersList(wasCorrect, questions);
        if (!hadWrongAnswers) {
            displayAllCorrectMessage();
        }
        displayEmptyLine();
    }

    public static void displayWrongAnswersHeader() {
        displayResultsSeparator();
        displayWrongAnswersTitle();
        displayResultsSeparator();
        displayEmptyLine();
    }

    public static void displayWrongAnswersTitle() {
        System.out.println("  PYTANIA Z BŁĘDNYMI ODPOWIEDZIAMI");
    }

    public static boolean displayWrongAnswersList(boolean[] wasCorrect, String[][] questions) {
        boolean hadWrongAnswers = false;
        for (int i = 0; i < wasCorrect.length; i++) {
            if (!wasCorrect[i]) {
                hadWrongAnswers = true;
                int questionNumber = calculateQuestionNumber(i);
                displaySingleWrongAnswer(questionNumber, questions[i][0]);
            }
        }
        return hadWrongAnswers;
    }

    public static void displaySingleWrongAnswer(int questionNumber, String questionText) {
        System.out.println("✗ Pytanie " + questionNumber + ": " + questionText);
    }

    public static void displayAllCorrectMessage() {
        System.out.println("🎉 Brawo! Wszystkie odpowiedzi poprawne!");
    }

    public static boolean wantsToRetry(Scanner scanner) {
        displayRetryPrompt();
        String answer = readRetryAnswer(scanner);
        return isRetryAnswer(answer);
    }

    public static void displayRetryPrompt() {
        displayResultsSeparator();
        System.out.println("Czy chcesz spróbować ponownie? (tak/nie)");
    }

    public static String readRetryAnswer(Scanner scanner) {
        return scanner.nextLine();
    }

    public static boolean isRetryAnswer(String answer) {
        return answer.equalsIgnoreCase("tak");
    }

    public static void displayGoodbye() {
        displayEmptyLine();
        displayGoodbyeSeparator();
        displayGoodbyeMessage();
        displayMotivationalMessage();
        displayFarewellMessage();
        displayGoodbyeSeparator();
        displayEmptyLine();
    }

    public static void displayGoodbyeSeparator() {
        displayResultsSeparator();
    }

    public static void displayGoodbyeMessage() {
        System.out.println("Dziękujemy za rozwiązanie quizu!");
    }

    public static void displayMotivationalMessage() {
        System.out.println("Pamiętaj: Praktyka czyni mistrza!");
    }

    public static void displayFarewellMessage() {
        System.out.println("Powodzenia w nauce Javy! 🚀");
    }
}