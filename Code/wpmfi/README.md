
# Weighted probabilistic maximal frequent itemset (WPMFIM)

# Source code:

## How to run project ?

- Di chuyển tới thư mục chứa code

### Compile code
- javac -d bin -cp lib/* pmfi/entities/\*.java pmfi/supports/\*.java pmfi/utils/\*.java pmfi/algorithms/\*.java pmfi/functions/\*.java test/\*.java Main.java

### Run
- java -cp ".;lib/commons-math3-3.6.1.jar;bin;" Main algorithms nameDataset minSupport minConfidence

- java -cp ".;lib/commons-math3-3.6.1.jar;bin;" Main 1 T40I10D100K_10K 0.1 0.6 (sử dụng thuật toán WPMFIM, dataset: T40I10D100K, min support: 0.3, min confidence: 0.9)

#### Giải thích tham số

- algorithms: [WPMFIM: 1, AWPMFIM: 2]

- nameDataset: tên bộ dữ liệu (T40I10D100K, connect4, accidents, US)

- minSupport: độ hỗ trợ tối thiểu

- minConfidence: độ tin cậy tối thiểu

### Dữ liệu mẫu

> Uncertain database

ID	    Giao dịch
TID1	{A: 0.5} {B: 0.7} {D: 0.8} {E: 0.9}
TID2	{B: 0.6} {C: 0.8} {D: 0.6} {E: 0.8} 
TID3	{C: 0.6} {D: 0.9} {E: 0.5}
TID4	{A: 0.6} {C: 0.7} {D: 0.8} {E: 0.8}
TID5	{A: 0.8} {B: 0.9} {C: 0.5} {D: 0.6} {E: 0.7}
TID6	{B: 0.6} {D: 0.9} {E: 0.8}

> Weighted table

Phần tử	    A	B	C	D	E
Trọng số	0.3	0.9	0.5	0.6	0.9

#### Thực thi chương trình

- compile code: javac -d bin -cp lib/* pmfi/entities/*.java pmfi/supports/*.java pmfi/utils/*.java pmfi/algorithms/*.java pmfi/functions/*.java test/*.java Main.java

- run code: java -cp ".;lib/commons-math3-3.6.1.jar;bin;" Main test