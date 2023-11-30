package assignment07;

public class BadHashFunctor implements HashFunctor{
    @Override
    public int hash(String string) {
        int hash = 0;
        // Summing character values of string
            //more likely to have repeat hash codes
        for (int i = 0; i < string.length(); i++) {
            hash += string.charAt(i);
        }
        return hash;
        }
}
