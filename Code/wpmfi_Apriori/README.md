
# Weighted probabilistic maximal frequent itemset (wPMFI_Apriori)

# Source code:

## How to run project ?

- Di chuyển tới thư mục chứa code

### Compile code
- javac -d bin -cp lib/* wPFI/entities/\*.java wPFI/supports/\*.java wPFI/utils/\*.java wPFI/algorithms/\*.java wPFI/test/\*.java Main.java

### Run
- java -cp ".;lib/commons-math3-3.6.1.jar;bin;" Main nameDataset minSupport minConfidence

- java -cp ".;lib/commons-math3-3.6.1.jar;bin;" Main T40I10D100K_10K 0.1 0.6 (dataset: T40I10D100K_10K, min support: 0.3, min confidence: 0.9)

#### Giải thích tham số

- nameDataset: tên bộ dữ liệu (T40I10D100K, connect4, accidents, US)

- minSupport: độ hỗ trợ tối thiểu

- minConfidence: độ tin cậy tối thiểu

### Dữ liệu mẫu

> Uncertain database

> Uncertain database

> ID	 ---   Giao dịch
>
> TID1	{A: 0.5} {B: 0.7} {D: 0.8} {E: 0.9}
>
> TID2	{B: 0.6} {C: 0.8} {D: 0.6} {E: 0.8}
>
> TID3	{C: 0.6} {D: 0.9} {E: 0.5}
>
> TID4	{A: 0.6} {C: 0.7} {D: 0.8} {E: 0.8}
>
> TID5	{A: 0.8} {B: 0.9} {C: 0.5} {D: 0.6} {E: 0.7}
>
> TID6	{B: 0.6} {D: 0.9} {E: 0.8}

> Weighted table

> {A : 0.3}	{B: 0.9}	{C: 0.5}	{D:0.6}	{E:0.9}

#### Thực thi chương trình

- Compile code: javac -d bin -cp lib/* wPFI/entities/\*.java wPFI/supports/\*.java wPFI/utils/\*.java wPFI/algorithms/\*.java wPFI/test/\*.java Main.java


- Run code: java -cp ".;lib/commons-math3-3.6.1.jar;bin;" Main test
