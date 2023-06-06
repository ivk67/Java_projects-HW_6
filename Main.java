/*Подумать над структурой класса Ноутбук для магазина техники - выделить поля и методы. Реализовать в java.
Создать множество ноутбуков.
Написать метод, который будет запрашивать у пользователя критерий (или критерии) фильтрации и выведет ноутбуки,
отвечающие фильтру. Критерии фильтрации можно хранить в Map. Например: “Введите цифру, соответствующую необходимому
критерию:
1 - ОЗУ
2 - Объем ЖД
3 - Операционная система
4 - Цвет …
Далее нужно запросить минимальные значения для указанных критериев - сохранить параметры фильтрации можно также в Map.
Отфильтровать ноутбуки из первоначального множества и вывести проходящие по условиям.

Работу сдать как обычно ссылкой на гит репозиторий.

Частые ошибки:
1. Заставляете пользователя вводить все существующие критерии фильтрации
2. Невозможно использовать более одного критерия фильтрации одновременно
3. При выборке выводятся ноутбуки, которые удовлетворяют только одному фильтру, а не всем введенным пользователем
4. Работа выполнена только для каких то конкретных ноутбуков, и если поменять характеристики ноутбуков или добавить
   еще ноутбук, то программа начинает работать некорректно */

package HW_6;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Map;

class Notebook {
    String brand;
    String color;
    String operatingSystem;
    double screenSize;
    int RAM;
    int HDD;
    double price;

    public Notebook(String brand, String color, String operatingSystem, double screenSize, int RAM, int HDD, double price) {
        this.brand = brand;
        this.color = color;
        this.operatingSystem = operatingSystem;
        this.screenSize = screenSize;
        this.RAM = RAM;
        this.HDD = HDD;
        this.price = price;
    }
}

public class Main {
    public static void main(String[] args) {
        List<Notebook> notebooks = new ArrayList<>();
        notebooks.add(new Notebook("Apple", "Silver", "macOS", 13.3, 8, 256, 1000));
        notebooks.add(new Notebook("Lenovo", "Black", "Windows", 15.6, 16, 512, 800));
        notebooks.add(new Notebook("Dell", "Gray", "Linux", 14, 16, 1000, 600));
        notebooks.add(new Notebook("HP", "Silver", "Linux", 14, 32, 512, 850));
        notebooks.add(new Notebook("ASUS", "Black", "Windows", 15.6, 16, 2000, 800));
        notebooks.add(new Notebook("Samsung", "Silver", "Linux", 17, 4, 1000, 750));

        Scanner scanner = new Scanner(System.in);
        Map<Integer, Object> filterCriteria = new HashMap<>();
        System.out.println("Введите цифру, соответствующую необходимому критерию:");
        System.out.println("1 - RAM от 4х до 32х\n2 - HDD\n3 - операционная система - macOS, Windows, Linux\n4 - Цвет - Silver, Black, Gray");
        System.out.println("Нажмите 'q' для выхода и получения результатов.");

        String input = "";
        while (!input.equalsIgnoreCase("q")) {
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("q")) break;

            int criterion = Integer.parseInt(input);
            System.out.print("Введите минимальный объём для RAM и HDD или тип для ОС и цвета: ");
            String minValue = scanner.nextLine();
            switch (criterion) {
                case 1:
                    filterCriteria.put(1, Integer.parseInt(minValue));
                    break;
                case 2:
                    filterCriteria.put(2, Integer.parseInt(minValue));
                    break;
                case 3:
                    filterCriteria.put(3, minValue);
                    break;
                case 4:
                    filterCriteria.put(4, minValue);
                    break;
            }
            System.out.println("Введите другой критерий или нажмите 'q' для окончания.");
        }

        final List<Notebook> filteredNotebooks = filterNotebooks(notebooks, filterCriteria);
  
        System.out.println("отфильтрованные ноутбуки:");
        for (Notebook notebook : filteredNotebooks) {
            System.out.println(notebook.brand + " " + notebook.color + " " + notebook.operatingSystem + " " + notebook.screenSize + "inches " + notebook.RAM + "GB " + notebook.HDD + "GB " + notebook.price + "USD");
        }
        scanner.close();
    }

    private static List<Notebook> filterNotebooks(List<Notebook> notebooks, Map<Integer, Object> filterCriteria) {
        List<Notebook> filteredNotebooks = new ArrayList<>(notebooks);
        for (Map.Entry<Integer, Object> entry : filterCriteria.entrySet()) {
            filteredNotebooks.removeIf(notebook -> {
                switch (entry.getKey()) {
                    case 1:
                        return notebook.RAM < (int) entry.getValue();
                    case 2:
                        return notebook.HDD < (int) entry.getValue();
                    case 3:
                        return !notebook.operatingSystem.equalsIgnoreCase((String) entry.getValue());
                    case 4:
                        return !notebook.color.equalsIgnoreCase((String) entry.getValue());
                    default:
                        return false;
                }
            });
        }
        return filteredNotebooks;
    }
}