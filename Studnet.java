package text.edu.xatu;

public class Studnet {
    private String name;
    private int age;
    private int namber;

    @Override
    public String toString() {
        return "Studnet{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", namber=" + namber +
                '}';
    }

    public Studnet(String name, int age, int namber) {
        this.name = name;
        this.age = age;
        this.namber = namber;
    }
    public Studnet(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getNamber() {
        return namber;
    }

    public void setNamber(int namber) {
        this.namber = namber;
    }
}
