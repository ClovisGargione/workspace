package util;

import dto.RetornoContatos;
import model.ContatoModel;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by c_r_s_000 on 04/06/2015.
 */
public class MetodosUteis {

    private Logger logger = Logger.getLogger(MetodosUteis.class);

    public ContatoModel montaContatoPorJSON(JSONObject jsonObject){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        ContatoModel contatoModel = new ContatoModel();
        contatoModel.setEmail(jsonObject.getString("email"));
        contatoModel.setNome(jsonObject.getString("nome"));
        contatoModel.setTelefone(Long.valueOf(jsonObject.getString("telefone").replaceAll("[^0-9]", "")));
        try {
            contatoModel.setDataNascimento(sdf.parse(jsonObject.getString("dataNascimento")));
            return contatoModel;
        } catch (ParseException e) {
            logger.error("Não foi possível fazer o parse da data de nascimento erro -> " + e.getMessage());
        }
        return null;
    }

    public List<RetornoContatos> montaListaCOntatosDTO(List<ContatoModel> contatos){
        List<RetornoContatos> retornoContatoses = new ArrayList<RetornoContatos>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        PhoneDecilmalFormat phoneDecilmalFormat = new PhoneDecilmalFormat();
        for(ContatoModel contatoModel : contatos){
            RetornoContatos retornoContatos = new RetornoContatos();
            retornoContatos.setId(contatoModel.getId());
            retornoContatos.setNome(contatoModel.getNome());
            retornoContatos.setEmail(contatoModel.getEmail());
            retornoContatos.setTelefone(phoneDecilmalFormat.formatter(contatoModel.getTelefone()));
            retornoContatos.setDataNascimento(sdf.format(contatoModel.getDataNascimento()));
            retornoContatoses.add(retornoContatos);
        }
        return retornoContatoses;
    }

    public RetornoContatos montaObjetoRetornoContatos(ContatoModel contatoModel){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        PhoneDecilmalFormat phoneDecilmalFormat = new PhoneDecilmalFormat();
        RetornoContatos retornoContatos = new RetornoContatos();
        retornoContatos.setId(contatoModel.getId());
        retornoContatos.setNome(contatoModel.getNome());
        retornoContatos.setEmail(contatoModel.getEmail());
        retornoContatos.setDataNascimento(sdf.format(contatoModel.getDataNascimento()));
        retornoContatos.setTelefone(phoneDecilmalFormat.formatter(contatoModel.getTelefone()));
        return  retornoContatos;
    }
}
