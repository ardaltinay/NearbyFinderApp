<template>
  <div class="container">
    <h2>Search Nearby Places</h2>
    <form class="row align-items-center justify-content-center g-3 mt-3" @submit.prevent="searchPlaces">
      <div class="col-auto d-flex align-items-center justify-content-center">
        <label for="">Longitude:</label>
        <input type="text" class="form-control ms-1" placeholder="Longitude" v-model="longitude">
      </div>
      <div class="col-auto d-flex align-items-center justify-content-center ">
        <label for="">Latitude:</label>
        <input type="text" class="form-control ms-1" placeholder="Latitude" v-model="latitude">
      </div>
      <div class="col-auto d-flex align-items-center justify-content-center">
        <label for="">Radius:</label>
        <input type="text" class="form-control ms-1" placeholder="Radius" v-model="radius">
      </div>
      <div class="col-auto">
        <button type="submit" class="btn btn-primary">Search</button>
      </div> 
    </form>
    <div v-if="loading">Loading...</div>
    <ul class="list-group list-group-flush mt-5" v-if="places.length && !error">
      <li class="list-group-item" v-for="place in places" :key="generateKey(place)">
        {{ place.name }} - lat: {{ place.latitude }}, lng: {{ place.longitude }}
      </li>
    </ul>
    <div class="mt-5 text-danger" v-if="error">{{ error }}</div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'InputForm',
  data() {
    return {
      longitude: '',
      latitude: '',
      radius: '',
      loading: false,
      places: [],
      error: null
    }
  },
  methods: {
    async searchPlaces() {
      this.loading = true;
      try {
        const response = await axios.get('/api/places/nearby', {
          params: {
            longitude: this.longitude,
            latitude: this.latitude,
            radius: this.radius
          }
        });
        const data = await response.data;
        this.places = data;
        this.error = null;
      } catch(error) {
        if (error.response != undefined && error.response != null) {
          this.error = error.response.data.message;
        } else {
          this.error = 'An error occurred while fetching nearby places. Details: ' + error;
        }
      } finally {
        this.loading = false;
      }
    },
    generateKey(place) {
      return place.latitude + place.longitude + place.radius;
    }
  }
}
</script>

<style>

</style>