package assignment07;

public class GoodHashFunctor implements HashFunctor {
    @Override
    public int hash(String string) {
        int hash = 0;
        //iterate through each character of the string
        for (int i = 0; i < string.length(); i++) {
            hash = 31 * hash + string.charAt(i); //31 is an odd prime number, which helps in reducing collisions
        }
        return hash;
    }
}
