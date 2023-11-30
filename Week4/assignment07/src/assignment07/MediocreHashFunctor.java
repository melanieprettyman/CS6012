package assignment07;

public class MediocreHashFunctor implements HashFunctor{
    @Override
    public int hash(String string) {
        int hash = 0;
        //loop through the characters of the string
        for (int i = 0; i < string.length(); i++) {
            hash = (hash * 32) + string.charAt(i); //multiplying by an even number, less even distribution, more collisions
        }
        return hash;
    }
}
