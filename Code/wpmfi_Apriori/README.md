
# Weighted probabilistic maximal frequent itemset (wPMFI_Apriori)

# Language: Java

# Enviroment:

> JDK 18 

# Data

- download data từ link: https://github.com/AceGnuh/data-cdnc vào thư mục: wPFI/datasets

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

![plot](../../Sơ%20đồ/Sample%20Data.png)

#### Thực thi chương trình

- Compile code: javac -d bin -cp lib/* wPFI/entities/\*.java wPFI/supports/\*.java wPFI/utils/\*.java wPFI/algorithms/\*.java wPFI/test/\*.java Main.java


- Run code: java -cp ".;lib/commons-math3-3.6.1.jar;bin;" Main test-ex1
