----------------------------------------arraySortRuntime() output----------------------------------------
  Enter 0 to test Arrays.sort(), 1 for unique, any other number to exit
  0
  Enter number of arrays to test
  20
  Enter largest array size
  2000
  N:100,T(n): 402900 nanoseconds,T(n)/n*n): 40.29,T(n/n*log(n)): 606.4249262650901
  N:200,T(n): 105200 nanoseconds,T(n)/n*n): 2.63,T(n/n*log(n)): 68.81343486074952
  N:300,T(n): 124100 nanoseconds,T(n)/n*n): 1.3788888888888888,T(n/n*log(n)): 50.27048015342023
  N:400,T(n): 111600 nanoseconds,T(n)/n*n): 0.6975,T(n/n*log(n)): 32.277260735786406
  N:500,T(n): 35599 nanoseconds,T(n)/n*n): 0.142396,T(n/n*log(n)): 7.9410788548414635
  N:600,T(n): 254000 nanoseconds,T(n)/n*n): 0.7055555555555556,T(n/n*log(n)): 45.8708040032572
  N:700,T(n): 61400 nanoseconds,T(n)/n*n): 0.1253061224489796,T(n/n*log(n)): 9.280745576032354
  N:800,T(n): 68399 nanoseconds,T(n)/n*n): 0.1068734375,T(n/n*log(n)): 8.865618515822892
  N:900,T(n): 73600 nanoseconds,T(n)/n*n): 0.0908641975308642,T(n/n*log(n)): 8.332953036589586
  N:1000,T(n): 67100 nanoseconds,T(n)/n*n): 0.0671,T(n/n*log(n)): 6.733037569684379
  N:1100,T(n): 102700 nanoseconds,T(n)/n*n): 0.08487603305785124,T(n/n*log(n)): 9.240916237771785
  N:1200,T(n): 91000 nanoseconds,T(n)/n*n): 0.06319444444444444,T(n/n*log(n)): 7.4136941545160004
  N:1300,T(n): 184300 nanoseconds,T(n)/n*n): 0.10905325443786983,T(n/n*log(n)): 13.705063353876408
  N:1400,T(n): 126799 nanoseconds,T(n)/n*n): 0.06469336734693877,T(n/n*log(n)): 8.666049639278144
  N:1500,T(n): 137800 nanoseconds,T(n)/n*n): 0.06124444444444444,T(n/n*log(n)): 8.707124579459776
  N:1600,T(n): 205301 nanoseconds,T(n)/n*n): 0.080195703125,T(n/n*log(n)): 12.055135160813903
  N:1700,T(n): 209199 nanoseconds,T(n)/n*n): 0.07238719723183391,T(n/n*log(n)): 11.467205004188848
  N:1800,T(n): 165400 nanoseconds,T(n)/n*n): 0.051049382716049384,T(n/n*log(n)): 8.497387478444738
  N:1900,T(n): 195600 nanoseconds,T(n)/n*n): 0.054182825484764545,T(n/n*log(n)): 9.451837387354074
  N:2000,T(n): 287100 nanoseconds,T(n)/n*n): 0.071775,T(n/n*log(n)): 13.09071893752135

----------------------------------------unique1Runtime() output----------------------------------------
  Enter 1 to test unique1, 2 for unique2, any other number to exit
  1
  Enter n value
  50
  Time Elapsed: 4.201E-6 secs
  
  Enter 1 to test unique1, 2 for unique2, any other number to exit
  1
  Enter n value
  200
  Time Elapsed: 8.3299E-5 secs
  
  Enter 1 to test unique1, 2 for unique2, any other number to exit
  1
  Enter n value
  10000
  Time Elapsed: 0.0089021 secs
  
  Enter 1 to test unique1, 2 for unique2, any other number to exit
  1
  Enter n value
  20000
  Time Elapsed: 0.0414535 secs
  Enter 1 to test unique1, 2 for unique2, any other number to exit
  1
  Enter n value
  50000
  Time Elapsed: 0.2876498 secs

----------------------------------------unique2Runtime() output----------------------------------------
  Enter 0 to test Arrays.sort(), 1 for unique, any other number to exit
  1
  Enter 1 to test unique1, 2 for unique2, any other number to exit
  2
  Enter n value
  50
  Time Elapsed: 0.0013168 secs
  
  Enter 1 to test unique1, 2 for unique2, any other number to exit
  2
  Enter n value
  200
  Time Elapsed: 9.12E-5 secs
  
  Enter 1 to test unique1, 2 for unique2, any other number to exit
  2
  Enter n value
  10000
  Time Elapsed: 0.0047697 secs
  
  Enter 1 to test unique1, 2 for unique2, any other number to exit
  2
  Enter n value
  20000
  Time Elapsed: 0.002702899 secs
  
  Enter 1 to test unique1, 2 for unique2, any other number to exit
  2
  Enter n value
  50000
  Time Elapsed: 0.0067888 secs
  
  Enter 1 to test unique1, 2 for unique2, any other number to exit
  2
  Enter n value
  100000
  Time Elapsed: 0.0096855 secs
  
  Enter 1 to test unique1, 2 for unique2, any other number to exit
  2
  Enter n value
  200000
  Time Elapsed: 0.0197698 secs
-------------------------------------------------------------------------------------------------------

➥arraySortRuntime() conclusion : T(n)/(n*n) is somewhat decreasing (it fluctuates but the maxSize array has always a much smaller value than the smallest array), and T(n)/(n log n) seems to converge towards 8. Which means that
the method behaves the as O(n log(n))

➥unique1Runtime() largest array size that runs in 1min :
Enter n value
760000
Time Elapsed: 60.4696676 secs

➥unique2Runtime() largest array size that runs in 1min :
Enter n value
630000000
Time Elapsed: 59.5642415 secs	