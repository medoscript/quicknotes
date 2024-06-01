1. Не использовать глаголы, в том числе обозначающие CRUD-операции.
   Использовать существительные.

   Неверно - Х
   Верно - V

   localhost:8080/products/deleteProduct?id=4 - X
   localhost:8080/products?id=4 - DELETE - V

2. Не использовать единственное число, если мы не обращаемся к ресурсу-синглтону.
   Использовать множественное число.

   localhost:8080/product?id=3 - X
   localhost:8080/products?id=3 - V
   localhost:8080/products/price-calculator?country=de

3. Не использовать camelCase, слитное написание слов и символ подчёркивания в именах ресурсов.
   Использовать дефис.

   localhost:8080/vendormanagement - X
   localhost:8080/vendorManagement - X
   localhost:8080/vendor_management - X
   localhost:8080/vendor-management - V

4. Не использовать расширения имён файлов.
   Просто опускать их.

   localhost:8080/products/registry.json - X
   localhost:8080/products/registry - V

5. Не использовать подстроку для поиска, фильтрации, сортировки.
   Использовать параметры.

   localhost:8080/products/100/150 - X
   localhost:8080/products?maxPrice=150&minPrice=100&sort=desc - V

6. Не использовать сокращённые и не несущие смысла имена параметров.
   Использовать осмысленные имена.

   localhost:8080/products?t=Banana - X
   localhost:8080/books?search=history - X
   localhost:8080/products?title=Banana - V
   localhost:8080/books?category=history - V

7. Не использовать разные форматы для ответа клиенту.
   Соблюдать единообразие.