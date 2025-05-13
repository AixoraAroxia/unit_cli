package com.unitTestGenerator.util.random.sevicesrandomimplement;



import com.unitTestGenerator.ioc.anotations.Component;
import com.unitTestGenerator.util.random.servicesRandom.IAddressRandomService;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class IAddressRandomServiceImplement implements IAddressRandomService {
    private Map<String, String[]> statesCityMap = new HashMap<String, String[]>();
    private Map<String, String[]> statesCountyMap = new HashMap<String, String[]>();
    private Random random = new Random();

    @Override
    public String generateAddress() {
        StringBuilder addString = new StringBuilder();
        String streetNunber = String.valueOf(random.nextInt(1000) + 1);
        String streetName = streetNames[random.nextInt(streetNames.length)];
        String state = this.states[random.nextInt(this.states.length)];
        String city = statesCityMap.get(state)[random.nextInt(statesCityMap.get(state).length)];
        String county = statesCountyMap.get(state)[random.nextInt(statesCountyMap.get(state).length)];
        Integer codeArea = generateRandomNumber(postalCodes[random.nextInt(postalCodes.length)],postalCodes[random.nextInt(postalCodes.length)]);
        String country = "Usa";
        addString.append(streetNunber).append(" ").append(streetName)
                .append(" ,").append(city)
                .append(" ,").append(state)
                .append(" ,").append(codeArea);
        return addString.toString();
    }

    @Override
    public String generateRandomStates() {
        String state = states[random.nextInt(states.length)];
        return state;
    }

    private Integer generateRandomNumber(Integer minNumber, Integer maxNumber) {
        Integer b = 0;
        if (minNumber > maxNumber) {
            b = new Random().nextInt(minNumber - maxNumber + 1) + maxNumber;
        } else {
            b = new Random().nextInt(maxNumber - minNumber + 1) + minNumber;
        }
        return b;
    }


    public boolean fillMaps() {

        boolean valor = false;
        statesCityMap.put("Alabama", Alabama);
        statesCityMap.put("Alaska", Alaska);
        statesCityMap.put("Arizona", Arizona);
        statesCityMap.put("Arkansas", Arkansas);
        statesCityMap.put("California", California);
        statesCityMap.put("Colorado", Colorado);
        statesCityMap.put("Connecticut", Connecticut);
        statesCityMap.put("Delaware", Delaware);
        statesCityMap.put("Florida", Florida);
        statesCityMap.put("Georgia", Georgia);
        statesCityMap.put("Hawaii", Hawaii);
        statesCityMap.put("Idaho", Idaho);
        statesCityMap.put("Illinois", Illinois);
        statesCityMap.put("Indiana", Indiana);
        statesCityMap.put("Iowa", Iowa);
        statesCityMap.put("Kansas", Kansas);
        statesCityMap.put("Kentucky", Kentucky);

        statesCountyMap.put("Alabama", AlabamaCounty);
        statesCountyMap.put("Alaska", AlaskaCounty);
        statesCountyMap.put("Arizona", ArizonaCounty);
        statesCountyMap.put("Arkansas", ArkansasCounty);
        statesCountyMap.put("California", CaliforniaCounty);
        statesCountyMap.put("Colorado", ColoradoCounty);
        statesCountyMap.put("Connecticut", ConnecticutCounty);
        statesCountyMap.put("Delaware", DelawareCounty);
        statesCountyMap.put("Florida", FloridaCounty);
        statesCountyMap.put("Georgia", GeorgiaCounty);
        statesCountyMap.put("Hawaii", HawaiiCounty);
        statesCountyMap.put("Idaho", IdahoCounty);
        statesCountyMap.put("Illinois", IllinoisCounty);
        statesCountyMap.put("Indiana", IndianaCounty);
        statesCountyMap.put("Iowa", IowaCounty);
        statesCountyMap.put("Kansas", KansasCounty);
        statesCountyMap.put("Kentucky", KentuckyCounty);

        if (statesCountyMap.size() > 0 && statesCityMap.size() > 0) {
            valor = true;
            return valor;
        }
        return valor;
    }


}
