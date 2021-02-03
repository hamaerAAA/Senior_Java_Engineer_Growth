#### 1、java内存屏障

[参考链接](https://blog.csdn.net/breakout_alex/article/details/94379895)

##### 1.1 什么是内存屏障

```
内存屏障是一种CPU指令。
	·确保一些特定操作执行的顺序
	·影响一些数据的可见性
		什么是可见性？即当前线程修改一个静态变量，这个时候就会去通		 知其他线程这个变量已经被修改了

	编译器和CPU可以在保证输出结果一致的情况下对指令进行排序，让性能更优。插入一个内存屏障，相当于告诉CPU和编译器内存屏障之前的指令必须在内存屏障之前执行，在内存屏障之后的指令必须在内存屏障之后再执行，就是第一点，保证执行的顺序。
	内存屏障强制更新一次不同的CPU内存，一个写屏障会把这个屏障前写入的数据刷新到缓存，这样任何试图读取该数据的线程将得到最新值
```

##### 1.2 java内存屏障

​	java中使用到内存屏障的主要有**synchronized关键字**、**volatile**关键字、**Unsafe**类中的**putOrderedObject()**与**putVolatiObject()**方法

###### 1.2.1 synchronized

```
当线程执行到synchronized关键字修饰的代码块后，线程读取变量信息时，保证读到的是最新的值,保证了数据的读有效性。在这里就是插入了StoreStore屏障
```

###### 1.2.2 volatile

```
使用了volatile修饰变量,则对变量的写操作,会插入StoreLoad屏障
```

###### 1.2.3 Unsafe

```
putOrderedObject() 插入的是StoreStore内存屏障
putVolatiObject() 插入的是StoreLoad屏障
```

```
LoadLoad: 操作序列 Load1 LoadLoad Load2 用于保证访问Load2的读取操作一定不能重排到Load1之前。
```

```
StoreStore 操作序列 Store1 StoreStore Store2 用于保证Store1及其之后的写出的数据一定先于Store2写出。就是其他CPU一定是先看到Store1的数据再看到Store2的数据。
```

```
LoadStore 操作序列Load1 LoadStore Store2  用于保证 Store2 及其之后写出的数据被其它 CPU 看到之前，Load1 读取的数据一定先读入缓存。甚至可能 Store2 的操作依赖于 Load1 的当前值
```

```
StoreLoad 操作序列 Store1, StoreLoad, Load2，用于保证 Store1 写出的数据被其它 CPU 看到后才能读取 Load2 的数据到缓存。
```

