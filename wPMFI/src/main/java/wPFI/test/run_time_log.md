
# RUN TIME

 ----------------------------------------------------------------------------------------

## Dataset: connect4
>Min Support: [0.1, 0.15, 0.2, 0.25, 0.35]
- Run Time:    [276,   87,  51,   13,    8] (s) - using probability model
- Run Time:    [637,  126, 130,   21,   15] (s) - not using probability model

>Min confidence:   0.9, 0.8, 0.6, 0.4, 0.2
- Run Time:   2,   4,  64, 124, 184 - using model
- Run Time:   2,   6,  104,342, 433 - none model

----------------------------------------------------------------------------------------

## T4100K - 10K line
>Min Support: [0.25, 0.2, 0.15, 0.1, 0.05]
- Run Time: [24839, 25886, 29260, 28863, 79606] (s)

>Min CONFIDENCE : [ 0.9, 0.8,  0.6,  0.4,   0.3,   0.2]
- Run Time:[  17,  18,   26,   93,   139] (s) - non probability model
- Run Time:[  27,  41,    44,  43,   49, 28] (s) - model

----------------------------------------------------------------------------------------

## Accidents - 10K line
>Min Support: [0.05, 0.1, 0.15, 0.2, 0.25, 0.3] - Min confidence: 0.6
- Run Time:    [ 256,  63,   39,  14,   10,  12] (s) - probability model
- Run Time:    [ 835, 214,  123,  45,   26,  26] (s) - not using probability model


> Min confidence: [ 0.9, 0.8,  0.6,  0.4,   0.3,   0.2] - Min support: 0.1n
- Run Time:       [   6,  10,   54,  126,   174,   236] (s) - probability model
- Run Time:       [   8,  30,   241, 552,   706,   ] (s) - non probability model