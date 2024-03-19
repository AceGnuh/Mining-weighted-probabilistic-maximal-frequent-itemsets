
# Project bao gồm những folder:

> Báo cáo: chứa file Report

> Code: chứa code các thuật toán 

- wpmfi: weighted probabilistic maximal frequent itemset cải biên từ PMFI

- wpmfi_apriori: weighted probabilistic maximal frequent itemset cải tiến từ wPFI_Apriori

> Tài liệu: chứa các tài liệu tham khảo

> wPFI: chứa code bài báo Efficient weighted probabilistic frequent itemset mining in uncertain databases 

# Source code:

## How to run project ?

- Di chuyển tới thư mục chứa code

### Compile code
- javac -d bin -cp lib/* pmfi/entities/*.java pmfi/supports/*.java pmfi/utils/*.java pmfi/algorithms/*.java pmfi/functions/*.java test/*.java Main.java

### Run
- java -cp ".;lib/commons-math3-3.6.1.jar;bin;" Main algorithms nameDataset minSupport minConfidence

#### Giải thích tham số

- algorithms: [WPMFIM: 1, AWPMFIM: 2]

- nameDataset: tên bộ dữ liệu

- minSupport: độ hỗ trợ tối thiểu

- minConfidence: độ tin cậy tối thiểu

## Structure of project:

-  test: chứa các class để test các dataset

-  dataset: chứa các dataset

-  utils: chứa code đọc các dataset

-  entities: chứa các class để định nghĩa uncertain database, uncertain transaction, uncertain database

-  entities/supports: chứa các class tính toán support, expected support, summed support probabilistic vector, probabilistic support,...

-  pmfit: chứa class implement thuật toán: Probabilistic Maximal Frequent Itemset Tree, Approximate Probabilistic Maximal Frequent Itemset Tree, Node Tree,...

-  functions: chứa các interface
