<template>
  <div class="container">
    <h2>Search Nearby Places</h2>
    <form class="row align-items-center justify-content-center g-3" @submit.prevent="searchPlaces">
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
    <ul v-if="places.length">
      <li v-for="place in places" :key="place.name">
        {{ place.name }} - {{ place.latitude }}, {{ place.longitude }}
      </li>
    </ul>
    <div v-if="error">{{ error }}</div>
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
        const data = await response.json();
        this.places = data;
      } catch(error) {
        this.error = 'An error occurred while fetching nearby places. Details: ' + error;
      } finally {
        this.loading = false;
      }
    }
  }
}
</script>

<style>

</style>