/**
 * 
 */
package model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author c_r_s_000
 *
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@javax.persistence.SequenceGenerator(name = "SEQUSUARIOS",   sequenceName = "SEQUSUARIOS",   allocationSize = 1, initialValue = 1)
public class UsuarioModel {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator = "SEQUSUARIOS")
	private Long id;
	
	@Column(length=60)
	private String nome;
	
	@Column(length=20)
	private String login;
	
	@Column(length=200)
	private String senha;

	@Column(length=50)
	private String email;
	
	@Column(length=100)
	private String localImagem;

	@OneToMany(cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch=FetchType.EAGER)
	@JoinColumn(name="usuario_id")
	private List<ContatoModel> contatos;

	@OneToMany(cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch=FetchType.EAGER)
	@JoinColumn(name="usuario_id")
	private List<EmailModel> emails;
	/**
	 * @return the id
	 */
	
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}
	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}
	/**
	 * @return the senha
	 */
	public String getSenha() {
		return senha;
	}
	/**
	 * @param senha the senha to set
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
		
	}
	/**
	 * @return the localImagem
	 */
	public String getLocalImagem() {
		return localImagem;
	}
	/**
	 * @param localImagem the localImagem to set
	 */
	public void setLocalImagem(String localImagem) {
		this.localImagem = localImagem;
	}


	public List<ContatoModel> getContatos() {
		return contatos;
	}

	public void setContatos(List<ContatoModel> contatos) {
		this.contatos=contatos;
	}

	public List<EmailModel> getEmails() {
		return emails;
	}

	public void setEmails(List<EmailModel> emails) {
		this.emails = emails;
	}
}
