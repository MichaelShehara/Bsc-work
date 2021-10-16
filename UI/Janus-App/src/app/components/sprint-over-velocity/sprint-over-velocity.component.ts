import { Component, OnInit } from '@angular/core';
import { NbToastrService } from '@nebular/theme';
import { ChartDataSets, ChartOptions, ChartType } from 'chart.js';
import * as moment from 'moment';
import { Color, Label } from 'ng2-charts';
import { ReportsService } from 'src/app/service/reports.service';
import { SprintService } from 'src/app/service/sprint.service';
import { StateService } from 'src/app/service/state.service';

@Component({
  selector: 'app-sprint-over-velocity',
  templateUrl: './sprint-over-velocity.component.html',
  styleUrls: ['./sprint-over-velocity.component.css']
})

export class SprintOverVelocityComponent implements OnInit {

  sprintChartData: any = [];
  projectID: any;
  barChartData: ChartDataSets[] = [
    {
      data: [],
      label: 'Actual'
    },
  ];
  barChartLabels: Label[] = [];
  barChartOptions: ChartOptions = {
    responsive: true,
  };
  barChartLegend = true;
  barChartType: ChartType = 'bar';
  barChartPlugins = [];
  barChartColors: Color[] = [
    {
      backgroundColor: 'transparent',
      borderColor: '#00d68f',
    },
    {
      backgroundColor: 'transparent',
      borderColor: '#BC2550',
    },
  ];
  loadingVisibile = true;

  constructor(
    private reportsService: ReportsService,
    private sprintService: SprintService,
    private stateService: StateService,
    private toastrService: NbToastrService
  ) { }

  ngOnInit() {
    // this.stateService.selectedProjectId.subscribe((data) => {
    //   this.projectID = Number(data);
    //   if (this.projectID !== 0) {
    //     this.reportsService.getSprintChartBySprintID(this.projectID).subscribe((data) => {
    //       if (data) {
    //         this.sprintChartData.push(data);
    //         if (this.sprintChartData.length !== 0) {
    //           this.sprintChartData[0].forEach(element => {
    //             if (element) {
    //               this.barChartData[0].data.push(element.balanceStoryPoint);
    //               this.barChartLabels.push(element.storyPoint);
    //             }
    //           });
    //         }
    //       }
    //     });
    //   } else {
    //   }
    // });
    this.stateService.selectedProjectId.subscribe((projectNumber) => {
      this.projectID = Number(projectNumber);
      this.sprintChartData = [];
      this.barChartData[0].data = [];
      this.barChartLabels = [];

      this.loadingVisibile = true;
      if (this.projectID !== 0) {
        this.reportsService.getSprintChartBySprintID(this.projectID).subscribe((data) => {
          if (data) {
            if (!this.sprintChartData.some(el => el.id === data.id)){
              this.sprintChartData.push(data);
            }
            console.log(this.sprintChartData)
          
            if (this.sprintChartData.length !== 0) {
              this.sprintChartData[0].forEach(element => {
                if (element) {
                  console.log(element)
                  if (!this.sprintChartData[0].some((el) => {
                    el.id === element.id
                    console.log(el.id)
                  }) &&
                  !this.barChartLabels.some(el => el === element.sprint.name)){
                  this.barChartData[0].data.push(element.balanceStoryPoint);
                  this.barChartLabels.push(element.sprint.name);
                  console.log(this.barChartData[0].data)
                  }
                }
              });
            }
            this.loadingVisibile = false;
          }
        });
      } 
    });
  }

  chartClicked({ event, active }: { event: MouseEvent, active: {}[] }): void {
  }

}
