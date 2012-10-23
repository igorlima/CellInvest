package com.eatj.igorribeirolima.fuzzylogic.model.service.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;

import br.ufla.lemaf.commons.model.service.to.MessageReturnTO;
import br.ufla.lemaf.commons.model.service.to.ObjectAndMessageReturnTO;
import br.ufla.lemaf.commons.model.service.to.ReturnTO;

import com.eatj.igorribeirolima.fuzzylogic.model.service.to.IndicadoresTecnicoTO;
import com.eatj.igorribeirolima.util.dadosinfomoney.DadosDeEntradaModelagem;
import com.eatj.igorribeirolima.util.dadosinfomoney.DadosInfoMoney;
import com.eatj.igorribeirolima.util.dadosinfomoney.dado.DadoAnaliseTecnica;

@Named
public class InfoMoneyBO {
  
  private final Map<String, DadosInfoMoney> map = new HashMap<String, DadosInfoMoney>();
	
  public ReturnTO getDadosInfoMoney( String strAtivo ) {
    DadosInfoMoney dadosInfoMoney = getDados(strAtivo);
    DadosDeEntradaModelagem dadosModelagem = new DadosDeEntradaModelagem(dadosInfoMoney);
    List<DadoAnaliseTecnica> indicadoresTecnicos = dadosInfoMoney.convert();
    
    IndicadoresTecnicoTO to = new IndicadoresTecnicoTO(indicadoresTecnicos, dadosModelagem);
    if (CollectionUtils.isEmpty(indicadoresTecnicos))
      return new MessageReturnTO(ReturnTO.Status.ERROR, "Não foi possível encontrar esse ativo");
    else
      return new ObjectAndMessageReturnTO<IndicadoresTecnicoTO>(to);

  }
  
  //aciona às 5h, 11h e 17h de segunda a sexta
  @Scheduled(cron="0 0 5,11,17 * * SUN-SAT")
  public synchronized void esvaziar_map(){
    System.out.println( "limpando map dos dados da Info Money..." );
    map.clear();
  }
	
  private DadosInfoMoney getDados( String strAtivo ){
    
    if( map.get( strAtivo ) == null )
      map.put(strAtivo, new DadosInfoMoney(strAtivo));
    
    return map.get( strAtivo );
  }
}
