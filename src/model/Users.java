package model;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@RequestScoped
public class Users {

	@PersistenceContext(unitName="auth")
	private EntityManager em;

	public User auth(String userID, String password) {
		String jpql = "SELECT u FROM User u WHERE u.userID=:u AND u.password=:p";
		List<User> found = em.createQuery(jpql, User.class)
				.setParameter("u", userID).setParameter("p", password)
				.getResultList();
		return found.size() == 1 ? found.get(0) : null;
	}

	@Transactional
	public User addUser(User u) {
		em.persist(u);
		return u;
	}
}
