package com.company;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        String input = "";
        String output = "";

        System.out.println("Введите путь к исходному файлу.");
        input = scan.nextLine();

        System.out.println("Введите путь к целевому файлу.");
        output = scan.nextLine();


        boolean result = WriteFileWitoutDuplicate(input, output);
        System.out.println(result ? "Копирование строк прошло успшно." : "Ошибка копирования!");
    }

    /**
     * Метод копирует строки исходного файла в целевой. Устраняет повторение строк, в конец каждой строки дописывает кол-во строк в виде [n]
     * @param input - путь к исходному файлу
     * @param output - путь к целевому файлу
     * @return Возвращает true - если запись прошла успешно, в противном случаи возвращает false
     */
    public static  boolean WriteFileWitoutDuplicate(String input, String output) throws IOException {
        FileReader inputStream;
        FileWriter outputStream = null;

        Map<String, Integer> resultText = new TreeMap<>();

        //Различные проверки
        //------------------------------------------------------------------------
        if(input.equals(output))
        {
            System.out.println("Исходный и конечный файлы не могут быть одинаковыми.");
            return false;
        }

        try {
            inputStream = new FileReader(input);
        } catch (FileNotFoundException e) {
            System.out.println("Файл " + input + " не найден");
            return false;
        }

        File out = new File(output);

        if(!out.exists()) {
            try {
                out.createNewFile();
                System.out.println("Создан целевой файл " + output);
            } catch (IOException e) {
                System.out.println("Ошибка создания целевого файла " + e.getMessage());
                return false;
            }
        }

        outputStream = new FileWriter(output, true);

        //----------------------------------------------------------------------------

        BufferedReader bufferedReader = new BufferedReader(inputStream);
        String line;
        Integer oldValue = 0;

        //Первую строку просто добавляем
        resultText.put(bufferedReader.readLine(), 0);

        //Формируем конечный текст
        while((line = bufferedReader.readLine()) != null){
            if(resultText.containsKey(line)) {
                oldValue = resultText.get(line);
                resultText.replace(line, oldValue, oldValue + 1);
            }
            else
                resultText.put(line, 0);
        }

        for(Map.Entry<String, Integer> entry : resultText.entrySet()){
            outputStream.append(entry.getKey() + " [" + entry.getValue() + "]\n");
        }

        outputStream.flush();

        outputStream.close();
        inputStream.close();
        return true;
    }
}