package david.api;

import david.model.SmartWord;

import java.util.ArrayList;

public interface ISmartWordDao {

    boolean insertSmartWord(SmartWord smartWord);
    ArrayList<SmartWord> getAllSmartWords();
    boolean deleteWord(int id);
    boolean updateSmartWord(SmartWord smartWord);
    ArrayList<SmartWord> searchSmartWords(String word);
}
