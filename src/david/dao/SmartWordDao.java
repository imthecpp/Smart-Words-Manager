package david.dao;


import david.api.ISmartWordDao;
import david.conn.ConnectionFactory;
import david.model.SmartWord;

import java.sql.*;
import java.util.ArrayList;

public class SmartWordDao implements ISmartWordDao{

    @Override
    public ArrayList<SmartWord> getAllSmartWords(){

        Connection connection = ConnectionFactory.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM smart_word");
            ArrayList<SmartWord> smartWords = new ArrayList<>();

            while (resultSet.next()){
                SmartWord smartWord = extractSmartWordFromDb(resultSet);
                smartWords.add(smartWord);
                //System.out.println(smartWord.getName());
            }

            return smartWords;
        }catch (SQLException ex){
            ex.printStackTrace();
        }

        return null;
    }
    @Override
    public boolean insertSmartWord(SmartWord smartWord){

        Connection connection = ConnectionFactory.getConnection();
        if(smartWord != null){
            try{
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO smart_word (name, definition) VALUES (?, ?)");
                preparedStatement.setString(1, smartWord.getName());
                preparedStatement.setString(2, smartWord.getDefinition());

                int wasAlright = preparedStatement.executeUpdate();

                if(wasAlright == 1){
                    return true;
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return false;
    }
    @Override
    public boolean deleteWord(int id){

        Connection connection = ConnectionFactory.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM smart_word WHERE id =?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateSmartWord(SmartWord smartWord) {
        Connection connection = ConnectionFactory.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE smart_word SET name=?, definition=? WHERE id=?");
            preparedStatement.setString(1,smartWord.getName());
            preparedStatement.setString(2,smartWord.getDefinition());
            preparedStatement.setInt(3, smartWord.getId());

            int wasAlright = preparedStatement.executeUpdate();

            if(wasAlright == 1){
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public ArrayList<SmartWord> searchSmartWords(String word) {

        Connection connection = ConnectionFactory.getConnection();
        try {
            Statement statement = connection.createStatement();
           // ResultSet resultSet = statement.executeQuery("SELECT * FROM smart_word");

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM smart_word WHERE name LIKE ?");
            preparedStatement.setString(1, "%"+word);

            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<SmartWord> smartWords = new ArrayList<>();
            while (resultSet.next()){
                SmartWord smartWord = extractSmartWordFromDb(resultSet);
                smartWords.add(smartWord);
                //System.out.println(smartWord.getName());
            }
            return smartWords;

        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    private SmartWord extractSmartWordFromDb(ResultSet resultSet) throws SQLException{
        SmartWord smartWord = null;

        if(resultSet != null){
            smartWord = new SmartWord(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("definition"));
            return smartWord;
        }

        return null;
    }

}
