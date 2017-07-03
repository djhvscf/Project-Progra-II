package com.randomquestions.user;

import com.randomquestions.dao.DAOConnection;
import com.randomquestions.dao.Repository;
import com.randomquestions.product.ProductRepository;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserRepository implements Repository<UserBean>
{
    final static Logger log = Logger.getLogger(ProductRepository.class);

    /**
     * Find one item by id.
     *
     * @param id
     * @return
     */
    @Override
    public UserBean find(long id)
    {
        log.debug("Start method...");

        UserBean obj = null;

        try
        {
            PreparedStatement prepared = DAOConnection.getInstance().prepareStatement(
                    "SELECT * FROM user WHERE id=?");

            prepared.setLong(1, id);

            ResultSet result = prepared.executeQuery();

            if (result.first())
            {
                obj = map(result);
            }

        } catch (SQLException e)
        {
            log.error("Error finding user : " + e);
        }

        log.debug("End method.");

        return obj;

    }

    /**
     * Find one item by id.
     *
     * @param username
     * @param password
     * @return
     */
    public UserBean login(String username, String password)
    {
        log.debug("Start method...");

        UserBean obj = null;

        try
        {
            PreparedStatement prepared = DAOConnection.getInstance().prepareStatement(
                    "SELECT * FROM user WHERE username=? AND password=?");

            prepared.setString(1, username);
            prepared.setString(2, password);

            ResultSet result = prepared.executeQuery();

            if (result.first())
            {
                obj = map(result);
            }

        } catch (SQLException e)
        {
            log.error("Error finding user : " + e);
        }

        log.debug("End method.");

        return obj;

    }

    /**
     * Find all items.
     *
     * @return
     */
    @Override
    public ArrayList<UserBean> findAll()
    {
        log.debug("Start method...");

        ArrayList<UserBean> users = new ArrayList<>();

        try
        {
            PreparedStatement prepared = DAOConnection.getInstance().prepareStatement(
                    "SELECT * FROM user");

            ResultSet result = prepared.executeQuery();

            while (result.next())
            {
                users.add(map(result));
            }

        } catch (SQLException e)
        {
            log.error("Error finding users : " + e);
        }

        log.debug("End method.");

        return users;
    }

    /**
     * Create new Object and return this new Object if success. Run only on
     * tables with auto_increment id column.
     *
     * @param obj
     * @return
     */
    @Override
    public UserBean create(UserBean obj)
    {
        log.debug("Start method...");

        UserBean objectToReturn = null;

        try
        {
            PreparedStatement prepared = DAOConnection.getInstance().prepareStatement(
                    " INSERT INTO user (name, username, email, password) "
                            + " VALUES(?, ?, ?, ?) ", Statement.RETURN_GENERATED_KEYS);

            prepared.setString(1, obj.getName());
            prepared.setString(2, obj.getUsername());
            prepared.setString(3, obj.getEmail());
            prepared.setString(4, obj.getPassword());

            // execute query and get the affected rows number :
            int affectedRows = prepared.executeUpdate();
            if (affectedRows != 0)
            {
                // get the latest inserted id :
                ResultSet generatedKeys = prepared.getGeneratedKeys();
                if (generatedKeys.next())
                {
                    log.debug("Inserted id : " + generatedKeys.getLong(1));
                    objectToReturn = this.find(generatedKeys.getLong(1));
                }
            }

        } catch (SQLException e)
        {
            log.error("Error creating new user : " + e);
        }

        log.debug("End method.");

        return objectToReturn;
    }

    /**
     * Update a record.
     *
     * @param obj
     * @return
     */
    @Override
    public UserBean update(UserBean obj)
    {
        log.debug("Start method...");

        UserBean objectToReturn = null;

        try
        {
            PreparedStatement prepared = DAOConnection.getInstance().prepareStatement(
                    " UPDATE user "
                            + " SET name=?, "
                            + " username=?, "
                            + " email=?, "
                            + " password=? "
                            + " WHERE id=? ");

            prepared.setString(1, obj.getName());
            prepared.setString(2, obj.getUsername());
            prepared.setString(3, obj.getEmail());
            prepared.setString(4, obj.getPassword());
            prepared.setLong(5, obj.getId());

            // execute query and get the affected rows number :
            int affectedRows = prepared.executeUpdate();
            if (affectedRows != 0)
            {
                log.debug("Updated id : " + obj.getId());
                objectToReturn = this.find(obj.getId());
            }

        } catch (SQLException e)
        {
            log.error("Error updating user : " + e);
        }

        log.debug("End method.");

        return objectToReturn;
    }

    /**
     * Delete a single record.
     *
     * @param id
     * @return the number of affected rows.
     */
    @Override
    public int delete(long id)
    {
        log.debug("Start method...");

        int affectedRows = 0;

        try
        {
            PreparedStatement prepared = DAOConnection.getInstance().prepareStatement(
                    " DELETE FROM user "
                            + " WHERE id=? ");

            prepared.setLong(1, id);

            // execute query and get the affected rows number :
            affectedRows = prepared.executeUpdate();

        } catch (SQLException e)
        {
            log.error("Error deleteing user : " + e);
        }

        log.debug("End method.");

        return affectedRows;
    }

    /**
     * Map the current row of the given ResultSet to an Object.
     *
     * @param resultSet
     * @return The mapped Object from the current row of the given ResultSet.
     * @throws SQLException If something fails at database level.
     */
    private static UserBean map(ResultSet resultSet) throws SQLException
    {
        UserBean obj = new UserBean();

        obj.setId(resultSet.getLong("id"));
        obj.setName(resultSet.getString("name"));
        obj.setEmail(resultSet.getString("email"));
        obj.setPassword(resultSet.getString("password"));
        obj.setUsername(resultSet.getString("username"));

        return obj;
    }
}
