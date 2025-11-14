package com.betalyze.favorites.interfaces.rest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betalyze.favorites.application.outboundservices.ExternalFavoriteTeamService;
import com.betalyze.favorites.application.outboundservices.ExternalUserManegementService;
import com.betalyze.favorites.domain.model.command.AddFavoriteTeamCommand;
import com.betalyze.favorites.domain.model.command.RemoveFavoriteTeamCommand;
import com.betalyze.favorites.domain.model.query.GetFavoriteTeamsQuery;
import com.betalyze.favorites.domain.service.TeamFavoriteCommandService;
import com.betalyze.favorites.domain.service.TeamFavoriteQueryService;
import com.betalyze.favorites.infrastructure.resources.FavoriteTeamResource;
import com.betalyze.favorites.interfaces.resources.ExistUserResource;
import com.betalyze.shared.application.dto.Response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/teams")
@AllArgsConstructor
@Tag(name = "Teams", description = "Team favorite endpoints")
@Slf4j
public class TeamFavoriteController {

  TeamFavoriteQueryService favoritesQueryService;
  TeamFavoriteCommandService favoritesCommandService;

  ExternalUserManegementService externalUserManagementService;
  ExternalFavoriteTeamService externalFavoriteTeamService;

  @PostMapping("/user/{userId}/favorite/{teamId}")
  @Operation(summary = "Add favorite team")
  public ResponseEntity<Response<Void>> addFavoriteTeam(@PathVariable UUID userId, @PathVariable UUID teamId) {
    try {
      log.info("[TeamFavoriteController.addFavoriteTeam] Adding favorite team for user {}", userId);
      favoritesCommandService.handle(new AddFavoriteTeamCommand(userId, teamId));

      log.info("[TeamFavoriteController.addFavoriteTeam] Added favorite team for user {}", userId);
      return ResponseEntity.created(null).build();
    } catch (Exception e) {
      log.error("[TeamFavoriteController.addFavoriteTeam] Error adding favorite team", e);
      return ResponseEntity.internalServerError().build();
    }
  }

  @DeleteMapping("/user/{userId}/favorite/{teamId}")
  @Operation(summary = "Remove favorite team")
  public ResponseEntity<Response<Void>> removeFavoriteTeam(@PathVariable UUID userId, @PathVariable UUID teamId) {
    try {
      favoritesCommandService.handle(new RemoveFavoriteTeamCommand(userId, teamId));

      return ResponseEntity.ok().build();
    } catch (Exception e) {
      log.error("[TeamFavoriteController.removeFavoriteTeam] Error removing favorite team", e);
      return ResponseEntity.internalServerError().build();
    }
  }

  @GetMapping("/user/{userId}/favorite")
  @Operation(summary = "Get favorite teams")
  public ResponseEntity<Response<List<FavoriteTeamResource>>> getFavoriteTeams(@PathVariable UUID userId) {
    try {

      log.info("[TeamFavoriteController.getFavoriteTeams] Getting favorite teams for user {}", userId);
      Optional<ExistUserResource> user = externalUserManagementService.getUserById(userId);
      if (user.isEmpty()) {
        log.error("[TeamFavoriteController.getFavoriteTeams] User not found");
        return ResponseEntity.badRequest().build();
      }

      log.info("[TeamFavoriteController.getFavoriteTeams] Found user {}", user.get().getEmail());
      List<FavoriteTeamResource> favorites = favoritesQueryService.handle(new GetFavoriteTeamsQuery(userId)).stream()
          .map(favTeam -> {
            return externalFavoriteTeamService.getFavoriteTeamByUserIdAndTeamId(userId, favTeam.getTeamId());
          })
          .filter(Optional::isPresent)
          .map(Optional::get)
          .toList();

      log.info("[TeamFavoriteController.getFavoriteTeams] Returning {} favorite teams for user {}", favorites.size(),
          userId);
      return ResponseEntity.ok(Response.success(favorites));
    } catch (Exception e) {
      log.error("[TeamFavoriteController.getFavoriteTeams] Error getting favorite teams", e);
      return ResponseEntity.internalServerError().build();
    }
  }
}
