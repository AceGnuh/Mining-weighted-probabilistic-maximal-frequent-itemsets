
# Project bao gồm những folder:

> Báo cáo: chứa file Report

> Code: chứa code các thuật toán 

- PMFI: Probabilistic maximal frequent itemset

- wPFI: weighted probabilistic maximal freuquent itemset

- PMFI_wPMFI: cải tiến từ thuật toán PMFI

- wPFI_wPMFi: cải tiến từ thuật toán wPFI

> Tài liệu: chứa các tài liệu tham khảo

> wPFI: chứa code bài báo Efficient weighted probabilistic frequent itemset mining in uncertain databases 

# Source code:

## How to run project ?

### Compile code
- javac Main.java 

### Run
- java Main

## Structure of project:

-  test: chứa các class để test các dataset

-  dataset: chứa các dataset

-  utils: chứa code đọc các dataset

-  entities: chứa các class để định nghĩa uncertain database, uncertain transaction, uncertain database

-  entities/supports: chứa các class tính toán support, expected support, summed support probabilistic vector, probabilistic support,...

-  pmfit: chứa class implement thuật toán: Probabilistic Maximal Frequent Itemset Tree, Approximate Probabilistic Maximal Frequent Itemset Tree, Node Tree,...

-  functions: chứa các interface
