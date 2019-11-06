package net.caidingke.chenxi.patterns;

/**
 * @author bowen
 */
public class Singleton {
    private static volatile Singleton singleton;
    private Singleton() {
    }
    //懒汉
    public static synchronized Singleton getInstance() {
        if (singleton == null) {
            singleton = new Singleton();
        }
        return singleton;
    }
    //双锁
    public static Singleton getSingleton() {
        if (singleton == null) {                         //Single Checked
            synchronized (Singleton.class) {
                if (singleton == null) {                 //Double Checked
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
    /*
    singleton = new Singleton();jvm即时编译中存在指令重排序的优化,也就是说下面123的顺序是不能保证的.
    即执行顺序可能是1-2-3也可能是1-3-2,如果是后者,则在3执行完毕,2未执行之前,被另一个线程抢占了,这是singleton就是非null的,
    就返回了,也就报错了,解决办法就是在声明的时候使用volatile.有些人认为volatile的原因是可见性.也就是可以保证线程在本地不会
    存有singleton副本,每次都去主存中读取,但其实不对,使用volatile的主要原因是使用了其另一个特性:禁止指令重排序优化,也就是说
    读操作必须在执行完1-2-3之后或者1-3-2之后,不存在执行到1-3然后读到值.从'先行发生原则'的角度理解的话,就是对于一个volatile
    变量的操作都先行发生于后面对这个变量的读操作.(后面只时间)
    private volatile static Singleton singleton;
    1.给instance 分配内存
    2.调用 Singleton 的构造函数来初始化成员变量
    3.将instance对象指向分配的内存空间（执行完这步 instance 就为非 null 了）*/

    public static void main(String[] args) {
    }

}
