package com.codedecoder.resturantlisting.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.codedecoder.resturantlisting.dto.RestaurantDTO;
import com.codedecoder.resturantlisting.entity.Restaurant;

@Mapper
public interface RestaurantMapper {
	
	RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);
	
	Restaurant mapRestaurantDTOToRestaurant(RestaurantDTO restaurantDTO);
	RestaurantDTO mapRestaurantToRestaurantDTO(Restaurant restaurant);

}
