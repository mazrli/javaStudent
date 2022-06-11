package student.javastudent.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServier {
    @Autowired
    private UserRepo repo;


    public List<User> listAll(){
        return (List<User>)repo.findAll();
    }

    public void save (User user){
        repo.save(user);
    }

    public User findUserById(Integer id) throws NoSuchUserError{
        Optional<User> optionalUser= repo.findById(id);

        if (optionalUser.isPresent()){
            return optionalUser.get();
        }
        throw new NoSuchUserError("Det finns inga user");
    }


    public void deleteUser(Integer id) throws NoSuchUserError{
        Optional<User> optionalUser = repo.findById(id);
        if (optionalUser.isPresent()){
            repo.deleteById(id);
        }
        else {
            throw new NoSuchUserError("Det finns inga user");
        }
    }
}
