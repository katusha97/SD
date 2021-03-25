# SD

Для команд есть интерфейс `Command`, который имеет
метод `call` и его реализуют все команды, которые есть
и будут. Каждая команда представляет собой один класс.
В объекте каждой команды хранятся переданные ей аргументы.
Команде для вызова передаются входной поток и аргументы.
Команда возвращает новый поток, который можно вывести
в output, или "скормить" другой команде в пайплайне.

Также есть абстрактный класс `AbstractCommand`, который
хранит имя команды и ее аргументы. Каждая команда 
наследуется от него.

Для того, чтобы добавлять новые команды я
создала класс `Factory`, который хранит в себе map из 
названия команды в его конструктор. У класса `Factory` 
есть метод create, который по имени и переданным 
аргументам создает экземпляр команды. 

Есть класс `Parser`, у которого основной метод - `parse`.
Этот метод принимает строку и парсит ее на команды по
пайплайнам и возвращает списков `Command`. Для того, чтобы
получить этот самый список Command, каждый кусок строки
разделяется на первый токен (обычно название команды) и
аргументы.

Работа с аргументами ведется в классе `Arguments`, он
ответственен за работу с одинарными и двойными кавычками,
подстановку переменных.

[Диаграмма](https://github.com/katusha97/SD/blob/f3c31fcb5721edda81c88dcc0ace6af6f60e7b56/diagram.png)