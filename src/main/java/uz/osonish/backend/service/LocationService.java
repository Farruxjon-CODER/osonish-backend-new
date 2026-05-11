package uz.osonish.backend.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LocationService {

    /**
     * Provides a mapping of Districts to Regions.
     * This is useful for implementing dependent dropdowns in the UI.
     */
    public Map<String, List<String>> getLocationHierarchy() {
        Map<String, List<String>> hierarchy = new HashMap<>();

        hierarchy.put("TASHKENT_CITY", List.of("Yunusabad", "Chilanzar", "Mirzo Ulugbek", "Yakkasaray", "Shaykhantakhur"));
        hierarchy.put("SAMARKAND", List.of("Samarkand City", "Pastdargom", "Bulungur", "Jomboy", "Taylak"));
        hierarchy.put("SIRDARYO", List.of("Guliston", "Shirin", "Yangiyer", "Boyovut", "Sirdaryo District"));
        hierarchy.put("FERGANA", List.of("Fergana City", "Margilan", "Kokand", "Quva", "Rishtan"));
        hierarchy.put("JIZZAKH", List.of("Jizzakh City", "Dzhizak District", "Gallaaral District", "Zomin District", "Sharof-Rashidon District"));
        hierarchy.put("SURXONDARYO", List.of("Boysun", "Denov", "Oltinsoy", "Sariosiyo", "Sherobod"));
        hierarchy.put("NAVoi", List.of("Navoiy City", "Navoiy District", "Khatirchi District", "Karmana District", "Qiziltepa District"));
        hierarchy.put("KASHKADARYO", List.of("Kashkadarya City", "Kashkadarya District", "Khatirchi District", "Karmana District", "Qiziltepa District"));
        hierarchy.put("KHOREZM", List.of("Khorezm City", "Khorezm District", "Khatirchi District", "Karmana District", "Qiziltepa District"));
        hierarchy.put("BUKHARA", List.of("Bukhara City", "Bukhara District", "Khatirchi District", "Karmana District", "Qiziltepa District"));
        hierarchy.put("ANDIJAN", List.of("Andijan City", "Andijan District", "Khatirchi District", "Karmana District", "Qiziltepa District"));
        hierarchy.put("NAMANGAN", List.of("Namangan City", "Namangan District", "Khatirchi District", "Karmana District", "Qiziltepa District"));
        hierarchy.put("KARAKALPAKSTAN", List.of("Karakalpakstan City", "Karakalpakstan District", "Khatirchi District", "Karmana District", "Qiziltepa District"));

        return hierarchy;
    }

    public List<Map<String, Object>> getRegionsWithId() {
        List<Map<String, Object>> regions = new ArrayList<>();
        regions.add(createRegionMap(1L, "TASHKENT_CITY", "Toshkent shahri"));
        regions.add(createRegionMap(2L, "SIRDARYO", "Sirdaryo viloyati"));
        regions.add(createRegionMap(3L, "SAMARKAND", "Samarqand viloyati"));
        regions.add(createRegionMap(4L, "FERGANA", "Fergana viloyati"));
        regions.add(createRegionMap(5L, "JIZZAKH", "Jizzakh viloyati"));
        regions.add(createRegionMap(6L, "SURXONDARYO", "Surxondaryo viloyati"));
        regions.add(createRegionMap(7L, "NAVoi", "Navoiy viloyati"));
        regions.add(createRegionMap(8L, "KASHKADARYO", "Kashkadaryo viloyati"));
        regions.add(createRegionMap(9L, "KHOREZM", "Khorezm viloyati"));
        regions.add(createRegionMap(10L, "BUKHARA", "Bukhara viloyati"));
        regions.add(createRegionMap(11L, "ANDIJAN", "Andijan viloyati"));
        regions.add(createRegionMap(12L, "NAMANGAN", "Namangan viloyati"));
        regions.add(createRegionMap(13L, "KARAKALPAKSTAN", "Karakalpakstan viloyati"));
        return regions;
    }

    private Map<String, Object> createRegionMap(Long id, String code, String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("code", code);
        map.put("name", name);
        return map;
    }
}
