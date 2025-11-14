package com.betalyze.favorites.application.outboundservices;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.betalyze.dataretrieval.interfaces.acl.DataRetrievalContextFacade;
import com.betalyze.dataretrieval.interfaces.resources.TeamResource;
import com.betalyze.favorites.domain.model.aggregate.FavoriteTeam;
import com.betalyze.favorites.infrastructure.persistance.jpa.repository.FavoriteTeamRepository;
import com.betalyze.favorites.infrastructure.resources.FavoriteTeamResource;
import com.betalyze.usermanagement.interfaces.acl.UserContextFacade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExternalFavoriteTeamService {

  private final FavoriteTeamRepository favoriteTeamRepository;
  private final UserContextFacade userContextFacade;
  private final DataRetrievalContextFacade dataRetrievalContextFacade;

  public Optional<FavoriteTeamResource> getFavoriteTeamByUserIdAndTeamId(UUID userId, UUID teamId) {
    Optional<FavoriteTeam> favoriteTeam = favoriteTeamRepository.findByUserIdAndTeamId(userId, teamId);

    if (favoriteTeam.isEmpty()) {
      log.info("Favorite team not found for userId {} and teamId {}", userId, teamId);
      return Optional.empty();
    }

    FavoriteTeam favoriteTeamToReturn = favoriteTeam.get();

    if (!userContextFacade.existUser(favoriteTeamToReturn.getUserId())) {
      log.warn("User not found for userId {}", favoriteTeamToReturn.getUserId());
      return Optional.empty();
    }

    Optional<TeamResource> team = dataRetrievalContextFacade.getTeamById(favoriteTeamToReturn.getTeamId());
    if (team.isEmpty()) {
      log.warn("Team not found for teamId {}", favoriteTeamToReturn.getTeamId());
      return Optional.empty();
    }

    return Optional.of(new FavoriteTeamResource(
        team.get().getId(),
        userId,
        team.get().getName(),
        team.get().getImgUrl()));
  }
}
